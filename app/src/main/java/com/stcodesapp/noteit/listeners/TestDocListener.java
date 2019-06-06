package com.stcodesapp.noteit.listeners;

import com.itextpdf.text.DocListener;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.HeaderFooter;
import com.itextpdf.text.Rectangle;

public class TestDocListener implements DocListener {
    @Override
    public void open() {

    }

    @Override
    public void close() {

    }

    @Override
    public boolean newPage() {
        return false;
    }

    @Override
    public boolean setPageSize(Rectangle rectangle) {
        return false;
    }

    @Override
    public boolean setMargins(float v, float v1, float v2, float v3) {
        return false;
    }

    @Override
    public boolean setMarginMirroring(boolean b) {
        return false;
    }

    @Override
    public boolean setMarginMirroringTopBottom(boolean b) {
        return false;
    }

    @Override
    public void setPageCount(int i) {

    }

    @Override
    public void resetPageCount() {

    }

    @Override
    public void setHeader(HeaderFooter headerFooter) {

    }

    @Override
    public void resetHeader() {

    }

    @Override
    public void setFooter(HeaderFooter headerFooter) {

    }

    @Override
    public void resetFooter() {

    }

    @Override
    public boolean add(Element element) throws DocumentException {
        return false;
    }
}
