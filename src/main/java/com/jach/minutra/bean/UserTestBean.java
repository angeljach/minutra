package com.jach.minutra.bean;

import com.jach.minutra.controller.UserController;
import com.jach.minutra.model.UserModel;
import com.jach.minutra.model.Users;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author jach
 */
@ManagedBean(name = "userTest")
@ViewScoped
public class UserTestBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private static final int CLIENT_ROWS_IN_AJAX_MODE = 15;
    private List<Users> allInventoryItems = null;
    
    private int currentCarIndex;
    private Users editedCar;
    private int page = 1;

    private int clientRows;
    
    
    
    private final UserModel modelUser = new UserModel();
    

    public void switchAjaxLoading(ValueChangeEvent event) {
        this.clientRows = (Boolean) event.getNewValue() ? CLIENT_ROWS_IN_AJAX_MODE : 0;
    }

    public void remove() {
        //TODO Cambiar
        allInventoryItems.remove(allInventoryItems.get(currentCarIndex));
    }

    public void store() {
        //TODO Cambiar
        //allInventoryItems.set(currentCarIndex, editedCar);
        
        (new UserController(editedCar)).update();
        this.allInventoryItems = modelUser.getUserList();
    }
    
//    public List<SelectItem> getVendorOptions() {
//        List<SelectItem> result = new ArrayList<SelectItem>();
//        result.add(new SelectItem("", ""));
//        for (InventoryVendorList vendorList : getInventoryVendorLists()) {
//            result.add(new SelectItem(vendorList.getVendor()));
//        }
//        return result;
//    }
//
//    public List<String> getAllVendors() {
//        List<String> result = new ArrayList<>();
//        for (InventoryVendorList vendorList : getInventoryVendorLists()) {
//            result.add(vendorList.getVendor());
//        }
//        return result;
//    }
//
//    public List<InventoryVendorList> getInventoryVendorLists() {
//        synchronized (this) {
//            if (inventoryVendorLists == null) {
//                inventoryVendorLists = new ArrayList<>();
//                List<Users> inventoryItems = getAllInventoryItems();
//
//                Collections.sort(inventoryItems, new Comparator<Users>() {
//                    public int compare(Users o1, Users o2) {
//                        return o1.getVendor().compareTo(o2.getVendor());
//                    }
//                });
//                Iterator<Users> iterator = inventoryItems.iterator();
//                InventoryVendorList vendorList = new InventoryVendorList();
//                vendorList.setVendor(inventoryItems.get(0).getVendor());
//                while (iterator.hasNext()) {
//                    Users item = iterator.next();
//                    InventoryVendorItem newItem = new InventoryVendorItem();
//                    itemToVendorItem(item, newItem);
//                    if (!item.getVendor().equals(vendorList.getVendor())) {
//                        inventoryVendorLists.add(vendorList);
//                        vendorList = new InventoryVendorList();
//                        vendorList.setVendor(item.getVendor());
//                    }
//                    vendorList.getVendorItems().add(newItem);
//                }
//                inventoryVendorLists.add(vendorList);
//            }
//        }
//        return inventoryVendorLists;
//    }
//
//    private void itemToVendorItem(Users item, InventoryVendorItem newItem) {
//        newItem.setActivity(item.getActivity());
//        newItem.setChangePrice(item.getChangePrice());
//        newItem.setChangeSearches(item.getChangeSearches());
//        newItem.setDaysLive(item.getDaysLive());
//        newItem.setExposure(item.getExposure());
//        newItem.setInquiries(item.getInquiries());
//        newItem.setMileage(item.getMileage());
//        newItem.setMileageMarket(item.getMileageMarket());
//        newItem.setModel(item.getModel());
//        newItem.setPrice(item.getPrice());
//        newItem.setPriceMarket(item.getPriceMarket());
//        newItem.setPrinted(item.getPrinted());
//        newItem.setStock(item.getStock());
//        newItem.setVin(item.getVin());
//    }

    public List<Users> getAllInventoryItems() {
        synchronized (this) {
            this.allInventoryItems = modelUser.getUserList();
        }
        return allInventoryItems;
    }

//    public List<Users> createCar(String vendor, String model, int count) {
//        ArrayList<Users> iiList = null;
//
//        try {
//            int arrayCount = count;
//            Users[] demoInventoryItemArrays = new Users[arrayCount];
//
//            for (int j = 0; j < demoUsersArrays.length; j++) {
//                Users ii = new Users();
//
//                ii.setVendor(vendor);
//                ii.setModel(model);
//                ii.setStock(RandomHelper.randomstring(6, 7));
//                ii.setVin(RandomHelper.randomstring(17, 17));
//                ii.setMileage(new BigDecimal(RandomHelper.rand(5000, 80000)).setScale(DECIMALS, ROUNDING_MODE));
//                ii.setMileageMarket(new BigDecimal(RandomHelper.rand(25000, 45000)).setScale(DECIMALS, ROUNDING_MODE));
//                ii.setPrice(new Integer(RandomHelper.rand(15000, 55000)));
//                ii.setPriceMarket(new BigDecimal(RandomHelper.rand(15000, 55000)).setScale(DECIMALS, ROUNDING_MODE));
//                ii.setDaysLive(RandomHelper.rand(1, 90));
//                ii.setChangeSearches(new BigDecimal(RandomHelper.rand(0, 5)).setScale(DECIMALS, ROUNDING_MODE));
//                ii.setChangePrice(new BigDecimal(RandomHelper.rand(0, 5)).setScale(DECIMALS, ROUNDING_MODE));
//                ii.setExposure(new BigDecimal(RandomHelper.rand(0, 5)).setScale(DECIMALS, ROUNDING_MODE));
//                ii.setActivity(new BigDecimal(RandomHelper.rand(0, 5)).setScale(DECIMALS, ROUNDING_MODE));
//                ii.setPrinted(new BigDecimal(RandomHelper.rand(0, 5)).setScale(DECIMALS, ROUNDING_MODE));
//                ii.setInquiries(new BigDecimal(RandomHelper.rand(0, 5)).setScale(DECIMALS, ROUNDING_MODE));
//                demoInventoryItemArrays[j] = ii;
//            }
//
//            iiList = new ArrayList<>(Arrays.asList(demoInventoryItemArrays));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return iiList;
//    }

    public int getCurrentCarIndex() {
        return currentCarIndex;
    }

    public void setCurrentCarIndex(int currentCarIndex) {
        this.currentCarIndex = currentCarIndex;
    }

    public Users getEditedCar() {
        return editedCar;
    }

    public void setEditedCar(Users editedCar) {
        this.editedCar = editedCar;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getClientRows() {
        return clientRows;
    }

    public void setClientRows(int clientRows) {
        this.clientRows = clientRows;
    }
    
}
