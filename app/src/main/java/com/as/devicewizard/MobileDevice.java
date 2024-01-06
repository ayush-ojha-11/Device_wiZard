package com.as.devicewizard;

public class MobileDevice {
    private String name;
    private String processor;
    private String price;
    private String image_url;
    private String store_url;

    public MobileDevice(){

    }
    public MobileDevice(String name, String processor, String price, String image_url, String store_url) {
        this.name = name;
        this.processor = processor;
        this.price = price;
        this.image_url = image_url;
        this.store_url = store_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getStore_url() {
        return store_url;
    }

    public void setStore_url(String store_url) {
        this.store_url = store_url;
    }
}
