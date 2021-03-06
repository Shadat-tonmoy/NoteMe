package com.stcodesapp.noteit.tasks.functionalTasks;

import android.app.Activity;
import android.support.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;
import com.stcodesapp.noteit.common.Logger;
import com.stcodesapp.noteit.common.Security;
import com.stcodesapp.noteit.constants.Constants;
import com.stcodesapp.noteit.constants.IAPIDs;
import com.stcodesapp.noteit.constants.IAPTypes;
import com.stcodesapp.noteit.models.ProductDetail;
import com.stcodesapp.noteit.tasks.functionalTasks.behaviorTrackingTasks.IAPTrackingTasks;

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

    public interface OnProductDetailFetchListener
    {
        void onProductDetailFetched(List<ProductDetail> productDetails);

    }

    private Activity activity;
    private OnPurchaseSuccessListener onPurchaseSuccessListener;
    private OnExistingPurchaseFetchListener onExistingPurchaseFetchListener;
    private OnProductDetailFetchListener onProductDetailFetchListener;
    private BillingClient billingClient;
    private IAPTrackingTasks iapTrackingTasks;


    public IAPBillingTasks(Activity activity, IAPTrackingTasks iapTrackingTasks) {

        this.activity = activity;
        this.iapTrackingTasks = iapTrackingTasks;
    }

    public void setupBillingClient()
    {
        billingClient = BillingClient.newBuilder(activity).setListener(this).build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    isServiceConnected = true;
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
            case IAPTypes.HALF_YEARLY_SUBS:
                skuList.add(IAPIDs.HALF_YEARLY_SUBS);
                break;
            case IAPTypes.YEARLY_SUBS:
                skuList.add(IAPIDs.YEARLY_SUBS);
                break;
            case IAPTypes.LIFE_TIME_PURCHASE:
                skuList.add(IAPIDs.LIFE_TIME_PURCHASE);
                break;
        }
        if(IAPType == IAPTypes.MONTHLY_SUBS || IAPType == IAPTypes.HALF_YEARLY_SUBS || IAPType == IAPTypes.YEARLY_SUBS)
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

    public void fetchAllProduct()
    {
        List<String> skuList = new ArrayList<> ();
        skuList.add(IAPIDs.MONTHLY_SUBS);
        skuList.add(IAPIDs.HALF_YEARLY_SUBS);
        skuList.add(IAPIDs.YEARLY_SUBS);
        skuList.add(IAPIDs.LIFE_TIME_PURCHASE);
        SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        final List<ProductDetail> productDetails = new ArrayList<>();
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS);
        billingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                        if(responseCode==BillingClient.BillingResponse.OK && skuDetailsList!=null)
                        {
                            for(SkuDetails skuDetails:skuDetailsList)
                            {
                                productDetails.add(new ProductDetail(skuDetails.getSku(),skuDetails.getPrice()));
                            }

                        }


                    }
                });
        params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
        billingClient.querySkuDetailsAsync(params.build(),
                new SkuDetailsResponseListener() {
                    @Override
                    public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                        if(responseCode==BillingClient.BillingResponse.OK && skuDetailsList!=null)
                        {
                            for(SkuDetails skuDetails:skuDetailsList)
                            {
                                productDetails.add(new ProductDetail(skuDetails.getSku(),skuDetails.getPrice()));
                            }
                            onProductDetailFetchListener.onProductDetailFetched(productDetails);
                        }


                    }
                });
    }

    private void queryPurchase() {
        Runnable queryPurchaseRequest = getQueryPurchaseRequest();
        executeRequest(queryPurchaseRequest);
    }


    private Runnable getQueryPurchaseRequest() {
        return new Runnable() {
            @Override
            public void run() {
                checkSubscription();
                checkLifeTimePurchase();






            }
        };
    }

    private void checkSubscription()
    {
        Purchase.PurchasesResult purchasesResult = billingClient.queryPurchases(BillingClient.SkuType.SUBS);
        final Set<Purchase> purchaseSet = new HashSet<>();

        if (isSubscriptionSupported()) {
            Purchase.PurchasesResult subscriptionResult
                    = billingClient.queryPurchases(BillingClient.SkuType.SUBS);

            if (subscriptionResult.getResponseCode() == BillingClient.BillingResponse.OK) {
                iapTrackingTasks.setPaidUser(false);
                for(Purchase purchase:subscriptionResult.getPurchasesList())
                {
                    if(verifyValidSignature(purchase.getOriginalJson(),purchase.getSignature()))
                    {
                        purchaseSet.add(purchase);
                        iapTrackingTasks.setPaidUser(true);
                        Logger.logMessage("ExistingPurchase",purchase.toString());
                    }
                }
                if(onExistingPurchaseFetchListener!=null)
                    onExistingPurchaseFetchListener.onExistingSubscriptionFetched(purchaseSet);
                Logger.logMessage("PaidUser",iapTrackingTasks.isPaidUser()+" is result");
            }
        }
        else if(purchasesResult.getPurchasesList()!=null)
        {
            purchasesResult.getPurchasesList().clear();
        }
    }

    private void checkLifeTimePurchase()
    {
        final Set<Purchase> purchaseSet = new HashSet<>();
        Purchase.PurchasesResult subscriptionResult
                = billingClient.queryPurchases(BillingClient.SkuType.INAPP);

        if (subscriptionResult.getResponseCode() == BillingClient.BillingResponse.OK) {
            iapTrackingTasks.setPaidUser(false);
            for(Purchase purchase:subscriptionResult.getPurchasesList())
            {
                if(verifyValidSignature(purchase.getOriginalJson(),purchase.getSignature()))
                {
                    purchaseSet.add(purchase);
                    iapTrackingTasks.setPaidUser(true);
                    Logger.logMessage("ExistingPurchase",purchase.toString());
                }
            }
            if(onExistingPurchaseFetchListener!=null)
                onExistingPurchaseFetchListener.onExistingSubscriptionFetched(purchaseSet);
            Logger.logMessage("PaidUser",iapTrackingTasks.isPaidUser()+" is result from lifetime purchase");
        }
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
                                    Logger.logMessage("PurchaseSuccess",purchase.toString());
                                    iapTrackingTasks.setPaidUser(true);
                                }
                            }
                        });
                    }
                    else
                    {
                        validPurchases.add(purchase);

                    }
                }
            }
            onPurchaseSuccessListener.onPurchaseSuccess(validPurchases);
        }
    }

    public void setOnPurchaseSuccessListener(OnPurchaseSuccessListener onPurchaseSuccessListener) {
        this.onPurchaseSuccessListener = onPurchaseSuccessListener;
    }

    public void setOnExistingPurchaseFetchListener(OnExistingPurchaseFetchListener onExistingPurchaseFetchListener) {
        this.onExistingPurchaseFetchListener = onExistingPurchaseFetchListener;
    }

    public void setOnProductDetailFetchListener(OnProductDetailFetchListener onProductDetailFetchListener) {
        this.onProductDetailFetchListener = onProductDetailFetchListener;
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
            default:
                return false;
        }
    }


}
