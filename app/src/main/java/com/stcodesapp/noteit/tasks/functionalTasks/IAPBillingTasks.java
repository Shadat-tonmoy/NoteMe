package com.stcodesapp.noteit.tasks.functionalTasks;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetailsParams;
import com.stcodesapp.noteit.common.Security;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.IAPIDs;
import com.stcodesapp.noteit.constants.IAPTypes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IAPBillingTasks implements PurchasesUpdatedListener {

    private boolean isServiceConnected;
    private String TAG = "IAPBillingTasks";


    public interface OnPurchaseSuccessListener {
        void onPurchaseSuccess(List<Purchase> purchases);
    }

    public interface OnExistingPurchaseFetchListener {
        void onExistingSubscriptionFetched(Set<Purchase> purchases);
    }

    public interface onRewardedVideoShowListener {
        void onShowRewardedVideo();

    }

    private Activity activity;
    private OnPurchaseSuccessListener onPurchaseSuccessListener;
    private OnExistingPurchaseFetchListener onExistingPurchaseFetchListener;
    private onRewardedVideoShowListener onRewardedVideoShowListener;
    private BillingClient billingClient;


    public IAPBillingTasks(Activity activity) {

        this.activity = activity;
    }

    public void setupBillingClient()
    {
        billingClient = BillingClient.newBuilder(activity).setListener(this).build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    isServiceConnected = true;
                    if(!Constants.IS_SUBSCRIBED_USER)
                        checkExistingIAP();
                }
            }
            @Override
            public void onBillingServiceDisconnected() {
            }
        });
    }

    public void setIAPModel(int IAPType)
    {
        if(billingClient==null ||  !billingClient.isReady())
        {
            setupBillingClient();
        }
        List<String> skuList = new ArrayList<>();
        switch (IAPType)
        {
            case IAPTypes.MONTHLY_SUBS:
                skuList.add(IAPIDs.MONTHLY_SUBS);
                break;
            case IAPTypes.YEARLY_SUBS:
                skuList.add(IAPIDs.YEARLY_SUBS);
                break;
            case IAPTypes.FIVE_K_CHARS:
                skuList.add(IAPIDs.FIVE_K_CHARS);
                break;
            case IAPTypes.TEN_K_CHARS:
                skuList.add(IAPIDs.TEN_K_CHARS);
                break;
            case IAPTypes.THIRTY_K_CHARS:
                skuList.add(IAPIDs.THIRTY_K_CHARS);
                break;
            case IAPTypes.FIFTY_K_CHARS:
                skuList.add(IAPIDs.FIFTY_K_CHARS);
                break;
            case IAPTypes.HUNDRED_K_CHARS:
                skuList.add(IAPIDs.HUNDRED_K_CHARS);
                break;
            case IAPTypes.TWO_HUNDRED_K_CHARS:
                skuList.add(IAPIDs.TWO_HUNDRED_K_CHARS);
                break;
            case IAPTypes.FIVE_HUNDRED_K_CHARS:
                skuList.add(IAPIDs.FIVE_HUNDRED_K_CHARS);
                break;
            case IAPTypes.WATCH_REWARDED_VIDEO:
                onRewardedVideoShowListener.onShowRewardedVideo();
                return;
        }
        if(IAPType == IAPTypes.MONTHLY_SUBS || IAPType == IAPTypes.YEARLY_SUBS)
            fetchSKUList(skuList,IAPTypes.SUBSCRIPTIONS);
        else fetchSKUList(skuList,IAPTypes.IN_APP_PURCHASE);
    }

    public void fetchSKUList(List<String> skuList,int IAPCategory)
    {
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        if(IAPCategory == IAPTypes.SUBSCRIPTIONS)
            startIAPBillingFlow(skuList.get(0), BillingClient.SkuType.SUBS);
        else
            startIAPBillingFlow(skuList.get(0), BillingClient.SkuType.INAPP);
    }

    private void startIAPBillingFlow(String skuID, String skuType)
    {
        BillingFlowParams flowParams = BillingFlowParams.newBuilder()
                .setSku(skuID)
                .setType(skuType)
                .build();
        billingClient.launchBillingFlow(activity,flowParams);
    }

    private void checkExistingIAP()
    {
        queryPurchase();
    }

    private void queryPurchase() {
        Runnable queryPurchaseRequest = getQueryPurchaseRequest();
        executeRequest(queryPurchaseRequest);
    }


    private Runnable getQueryPurchaseRequest() {
        return new Runnable() {
            @Override
            public void run() {
                Purchase.PurchasesResult purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.SUBS);
                final Set<Purchase> purchaseSet = new HashSet<>();

                if (isSubscriptionSupported()) {
                    Purchase.PurchasesResult subscriptionResult
                            = billingClient.queryPurchases(BillingClient.SkuType.SUBS);

                    if (subscriptionResult.getResponseCode() == BillingClient.BillingResponse.OK) {
                        for(Purchase purchase:subscriptionResult.getPurchasesList())
                        {
                            if(verifyValidSignature(purchase.getOriginalJson(),purchase.getSignature()))
                                purchaseSet.add(purchase);
                        }
                    }
                }
                else if(purchasesResult.getPurchasesList()!=null)
                {
                        purchasesResult.getPurchasesList().clear();
                }
            }
        };
    }

    private void startServiceConnection(final Runnable request) {
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(int responseCode) {
                if (responseCode == BillingClient.BillingResponse.OK) {
                    isServiceConnected = true;
                    if (request != null) {
                        request.run();
                    }
                }
            }
            @Override
            public void onBillingServiceDisconnected() {
                isServiceConnected = false;
            }
        });
    }

    private void executeRequest(Runnable request) {
        if (isServiceConnected) {
            if (request != null) {
                request.run();
            }
        } else {
            startServiceConnection(request);
        }
    }

    private boolean isSubscriptionSupported() {
        int responseCode = billingClient.isFeatureSupported(BillingClient.FeatureType.SUBSCRIPTIONS);
        if (responseCode != BillingClient.BillingResponse.OK) {
//            Log.e("SubscriptionSupport", "isSubscriptionSupported() got an error response: " + responseCode);
        }
        return responseCode == BillingClient.BillingResponse.OK;
    }

    public void killBillingClient()
    {
        billingClient.endConnection();
    }

    @Override
    public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
        if(responseCode== BillingClient.BillingResponse.OK && purchases!=null)
        {
            final List<Purchase> validPurchases = new ArrayList<>();
            for(final Purchase purchase:purchases)
            {
                if(verifyValidSignature(purchase.getOriginalJson(),purchase.getSignature()))
                {
                    if(isConsumableProduct(purchase.getSku()))
                    {
                        billingClient.consumeAsync(purchase.getPurchaseToken(), new ConsumeResponseListener() {
                            @Override
                            public void onConsumeResponse(int responseCode, String purchaseToken) {
                                if(responseCode== BillingClient.BillingResponse.OK)
                                {
                                    validPurchases.add(purchase);
                                    onPurchaseSuccessListener.onPurchaseSuccess(validPurchases);
                                }
                            }
                        });
                    }
                    else
                    {
                        validPurchases.add(purchase);
                        onPurchaseSuccessListener.onPurchaseSuccess(validPurchases);
                    }
                }
            }
        }
    }

    public void setOnPurchaseSuccessListener(OnPurchaseSuccessListener onPurchaseSuccessListener) {
        this.onPurchaseSuccessListener = onPurchaseSuccessListener;
    }

    public void setOnExistingPurchaseFetchListener(OnExistingPurchaseFetchListener onExistingPurchaseFetchListener) {
        this.onExistingPurchaseFetchListener = onExistingPurchaseFetchListener;
    }

    public void setOnRewardedVideoShowListener(IAPBillingTasks.onRewardedVideoShowListener onRewardedVideoShowListener) {
        this.onRewardedVideoShowListener = onRewardedVideoShowListener;
    }

    private boolean verifyValidSignature(String signedData, String signature) {
        if (Constants.BASE_64_ENCODED_PUBLIC_KEY.contains("CONSTRUCT_YOUR")) {
            throw new RuntimeException("Please update your app's public key at: "
                    + "BASE_64_ENCODED_PUBLIC_KEY");
        }
        try {
            return Security.verifyPurchase(Constants.BASE_64_ENCODED_PUBLIC_KEY, signedData, signature);
        } catch (IOException e) {
//            Log.e(TAG, "Got an exception trying to validate a purchase: " + e);
            return false;
        }
    }

    private boolean isConsumableProduct(String skuID)
    {
        switch (skuID){
            case IAPIDs.FIVE_K_CHARS:
                return true;
            case IAPIDs.TEN_K_CHARS:
                return true;
            case IAPIDs.THIRTY_K_CHARS:
                return true;
            case IAPIDs.FIFTY_K_CHARS:
                return true;
            case IAPIDs.HUNDRED_K_CHARS:
                return true;
            case IAPIDs.TWO_HUNDRED_K_CHARS:
                return true;
            case IAPIDs.FIVE_HUNDRED_K_CHARS:
                return true;
            default:
                return false;
        }
    }


}
