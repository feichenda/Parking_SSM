package com.lenovo.feizai.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lenovo.feizai.entity.*;
import com.lenovo.feizai.service.*;
import com.lenovo.feizai.util.FileUtil;
import com.lenovo.feizai.util.GsonUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author feizai
 * @date 02/22/2021 022 9:16:19 PM
 * @annotation
 */
@Controller
@RequestMapping("/api/merchant")
public class MerchantController {

    @Autowired
    ParkingInfoServiceDao parkingInfoServiceDao;

    @Autowired
    LocationServiceDao locationServiceDao;

    @Autowired
    RatesServiceDao ratesServiceDao;

    @Autowired
    ParkingNumberServerDao parkingNumberServerDao;

    @Autowired
    ParkingSpaceServiceDao parkingSpaceServiceDao;

    @Autowired
    MerchantStateServiceDao merchantStateServiceDao;

    @Autowired
    MerchantChangeServiceDao merchantChangeServiceDao;

    @Autowired
    CommentServiceDao commentServiceDao;

    @Autowired
    MerchantServiceDao merchantServiceDao;

    /**
     * 添加停车场
     *
     * @param merchant_str
     * @param location_str
     * @param rates_str
     * @param parkingnumber_str
     * @param license
     * @param image
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addmerchantinfo", method = RequestMethod.POST)
    public String addMerchantInfo(@Param("merchant_str") String merchant_str, @Param("location_str") String location_str, @Param("rates_str") String rates_str, @Param("parkingnumber_str") String parkingnumber_str, @RequestParam("license") MultipartFile[] license, @RequestParam("image") MultipartFile[] image) {
        ParkingInfo parkingInfo = GsonUtil.GsonToBean(merchant_str, ParkingInfo.class);
        Location location = GsonUtil.GsonToBean(location_str, Location.class);
        Rates rates = GsonUtil.GsonToBean(rates_str, Rates.class);
        ParkingNumber parkingnumber = GsonUtil.GsonToBean(parkingnumber_str, ParkingNumber.class);
        String certificate_paths = "";
        String photo_paths = "";
        BaseModel model = new BaseModel();
        ParkingInfo result = parkingInfoServiceDao.selectMerchantByMerchantName(parkingInfo.getMerchantname());
        if (result != null) {
            model.setCode(202);
            model.setMessage("商家名已存在");
            return GsonUtil.GsonString(model);
        }
        for (int i = 0; i < license.length; i++) {
            String path = null;
            try {
                path = FileUtil.getParkingFilePath(license[i], parkingInfo.getMerchantname(), "certificate_" + i);
            } catch (IOException e) {
                e.printStackTrace();
                model.setCode(201);
                model.setMessage("文件上传失败");
                return GsonUtil.GsonString(model);
            }
            if (!certificate_paths.equals("")) {
                certificate_paths = certificate_paths + "&" + path;
            } else {
                certificate_paths = certificate_paths + path;
            }
        }
        for (int i = 0; i < image.length; i++) {
            String path = null;
            try {
                path = FileUtil.getParkingFilePath(image[i], parkingInfo.getMerchantname(), "photo_" + i);
            } catch (IOException e) {
                e.printStackTrace();
                model.setCode(201);
                model.setMessage("文件上传失败");
                return GsonUtil.GsonString(model);
            }
            if (!photo_paths.equals("")) {
                photo_paths = photo_paths + "&" + path;
            } else {
                photo_paths = photo_paths + path;
            }
        }
        parkingInfo.setBusinesslicense(certificate_paths);
        parkingInfo.setMerchantimage(photo_paths);
        MerchantState state = new MerchantState();
        state.setMerchantname(parkingInfo.getMerchantname());
        state.setOperatingstate("未营业");
        state.setAuditstate("未审核");
        state.setRemark(null);
        //保存到数据库
        parkingInfoServiceDao.addParking(parkingInfo);
        locationServiceDao.addLocation(location);
        ratesServiceDao.addRates(rates);
        parkingNumberServerDao.addParkingNumber(parkingnumber);
        merchantStateServiceDao.addMerchantState(state);
        for (int i = 0; i < parkingnumber.getAllnumber(); i++) {
            ParkingSpace space = new ParkingSpace();
            space.setMerchantname(parkingnumber.getMerchantname());
            space.setParkingstate("未使用");
            space.setSerialnumber(String.valueOf(i + 1));
            parkingSpaceServiceDao.addSpace(space);
        }
        model.setCode(200);
        model.setMessage("文件上传成功");
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/selectParkingInfoByName")
    public String selectParkingInfoByName(@Param("merchantname") String merchantname) {
        BaseModel<MerchantProperty> model = new BaseModel<>();
        MerchantProperty property = new MerchantProperty();
        ParkingInfo parkingInfo = parkingInfoServiceDao.selectMerchantByMerchantName(merchantname);
        Location location = locationServiceDao.selectParkingByName(merchantname);
        ParkingNumber number = parkingNumberServerDao.selectNumberByMerchantnumber(merchantname);
        Rates rates = ratesServiceDao.findRatesByMerchant(merchantname);
        MerchantState merchantState = merchantStateServiceDao.findMerchantState(merchantname);
        if (parkingInfo == null || location == null || number == null || rates == null || merchantState == null) {
            model.setCode(201);
            model.setMessage("查询失败");
            return GsonUtil.GsonString(model);
        }
        property.setParkingInfo(parkingInfo);
        property.setLocation(location);
        property.setParkingNumber(number);
        property.setRates(rates);
        property.setMerchantState(merchantState);
        model.setCode(200);
        model.setMessage("查询成功");
        model.setData(property);
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping(value = "/addchengmerchantinfo", method = RequestMethod.POST)
    public String addchengmerchantinfo(@Param("oldname") String oldname, @Param("merchant_str") String merchant_str, @Param("location_str") String location_str, @Param("rates_str") String rates_str, @Param("parkingnumber_str") String parkingnumber_str, @RequestParam("license") MultipartFile[] license, @RequestParam("image") MultipartFile[] image) {
        ParkingInfo parkingInfo = GsonUtil.GsonToBean(merchant_str, ParkingInfo.class);
        Location location = GsonUtil.GsonToBean(location_str, Location.class);
        Rates rates = GsonUtil.GsonToBean(rates_str, Rates.class);
        ParkingNumber parkingnumber = GsonUtil.GsonToBean(parkingnumber_str, ParkingNumber.class);
        String certificate_paths = "";
        String photo_paths = "";
        BaseModel model = new BaseModel();
        ParkingInfo result = parkingInfoServiceDao.selectMerchantByMerchantName(parkingInfo.getMerchantname());
        if (result != null && !result.getMerchantname().equals(oldname)) {
            model.setCode(202);
            model.setMessage("商家名已存在");
            return GsonUtil.GsonString(model);
        }
        if (!FileUtil.deleteImageFile(oldname)) {
            model.setCode(201);
            model.setMessage("文件上传失败");
            return GsonUtil.GsonString(model);
        }
        for (int i = 0; i < license.length; i++) {
            String path = null;
            try {
                path = FileUtil.getParkingFilePath(license[i], parkingInfo.getMerchantname(), "certificate_" + i);
            } catch (IOException e) {
                e.printStackTrace();
                model.setCode(201);
                model.setMessage("文件上传失败");
                return GsonUtil.GsonString(model);
            }
            if (!certificate_paths.equals("")) {
                certificate_paths = certificate_paths + "&" + path;
            } else {
                certificate_paths = certificate_paths + path;
            }
        }
        for (int i = 0; i < image.length; i++) {
            String path = null;
            try {
                path = FileUtil.getParkingFilePath(image[i], parkingInfo.getMerchantname(), "photo_" + i);
            } catch (IOException e) {
                e.printStackTrace();
                model.setCode(201);
                model.setMessage("文件上传失败");
                return GsonUtil.GsonString(model);
            }
            if (!photo_paths.equals("")) {
                photo_paths = photo_paths + "&" + path;
            } else {
                photo_paths = photo_paths + path;
            }
        }
        parkingInfo.setBusinesslicense(certificate_paths);
        parkingInfo.setMerchantimage(photo_paths);
        MerchantState state = new MerchantState();
        state.setMerchantname(parkingInfo.getMerchantname());
        state.setOperatingstate("未营业");
        state.setAuditstate("未审核");
        state.setRemark(null);
        //保存到数据库
        parkingInfoServiceDao.readdParkingInfo(parkingInfo, oldname);
        locationServiceDao.readdLocation(location,oldname);
        ratesServiceDao.readdRates(rates,oldname);
        parkingNumberServerDao.readdParkingNumber(parkingnumber,oldname);
        merchantStateServiceDao.readdMerchantStatus(state,oldname);
        parkingSpaceServiceDao.deleteSpaceByMerchantName(oldname);
        for (int i = 0; i < parkingnumber.getAllnumber(); i++) {
            ParkingSpace space = new ParkingSpace();
            space.setMerchantname(parkingnumber.getMerchantname());
            space.setParkingstate("未使用");
            space.setSerialnumber(String.valueOf(i + 1));
            parkingSpaceServiceDao.addSpace(space);
        }
        model.setCode(200);
        model.setMessage("文件上传成功");
        return GsonUtil.GsonString(model);
    }

    /**
     * 查询所有停车场
     *
     * @param model
     * @return
     */
    @RequestMapping("/selectAllMerchant")
    public String SelectAllMerchant(@RequestParam(required = true, defaultValue = "1") int page, Model model) {
        PageHelper.startPage(page, 10);
        List<MerchantLoaction> result = parkingInfoServiceDao.selectMerchantBasicInfo();
        PageInfo<MerchantLoaction> pageInfo = new PageInfo<>(result);
        model.addAttribute("pagemsg", pageInfo);
        model.addAttribute("merchantinfo", result);
        model.addAttribute("getTotal", pageInfo.getTotal());
        return "merchantinfo";
    }

    @RequestMapping("/selectUncheckMerchant")//查询未审核的停车场
    public String selectUncheckMerchant(@RequestParam(required = true, defaultValue = "1") int page, Model model) {
        PageHelper.startPage(page, 10);
        List<MerchantLoaction> result = merchantStateServiceDao.selectUncheckMerchant(null);
        PageInfo<MerchantLoaction> pageInfo = new PageInfo<>(result);
        model.addAttribute("pagemsg", pageInfo);
        model.addAttribute("merchantinfo", result);
        model.addAttribute("getTotal", pageInfo.getTotal());
        return "auditinfo";
    }

    @RequestMapping("/selectUncheckChangeMerchant")//查询修改未审核的停车场
    public String selectUncheckChangeMerchant(@RequestParam(required = true, defaultValue = "1") int page, Model model) {
        PageHelper.startPage(page, 10);
        List<MerchantChange> result = merchantStateServiceDao.selectUncheckChangeMerchant(null);
        PageInfo<MerchantChange> pageInfo = new PageInfo<>(result);
        model.addAttribute("pagemsg", pageInfo);
        model.addAttribute("merchantinfo", result);
        model.addAttribute("getTotal", pageInfo.getTotal());
        return "changeinfo";
    }

    /**
     * 通过停车场名查询停车场
     *
     * @param name
     * @param model
     * @return
     */
    @RequestMapping("/selectMerchantByMerchantname")
    public String selectMerchantByMerchantname(String name, Model model) {
        ParkingInfo parkingInfo = parkingInfoServiceDao.selectMerchantByMerchantName(name);
        Location location = locationServiceDao.selectParkingByName(name);
        List<ParkingNumber> parkingNumbers = parkingNumberServerDao.selectNumberByname(name);
        List<MerchantState> merchantStates = merchantStateServiceDao.selectMerchantStateByName(name);
        List<Rates> rates = ratesServiceDao.selectRatesByMerchant(name);
        String license = parkingInfo.getBusinesslicense();
        String[] license_paths = license.split("&");
        String images = parkingInfo.getMerchantimage();
        String[] images_paths = images.split("&");
        model.addAttribute("merchantdetailedinfo", parkingInfo);
        model.addAttribute("locationdetailedinfo", location);
        model.addAttribute("parkingnumberdetailedinfo", parkingNumbers.get(0));
        model.addAttribute("merchantstatedetailedinfo", merchantStates.get(0));
        model.addAttribute("ratedetailedinfo", rates.get(0));
        model.addAttribute("license_paths", license_paths);
        model.addAttribute("images_paths", images_paths);
        return "merchantdetailedinfo";
    }

    @RequestMapping("/selectUncheckMerchantByMerchantname")//通过停车场名查询未审核的停车场
    public String selectUncheckMerchantByMerchantname(String name, Model model) {
        ParkingInfo parkingInfo = parkingInfoServiceDao.selectMerchantByMerchantName(name);
        Location location = locationServiceDao.selectParkingByName(name);
        List<ParkingNumber> parkingNumbers = parkingNumberServerDao.selectNumberByname(name);
        List<MerchantState> merchantStates = merchantStateServiceDao.selectMerchantStateByName(name);
        List<Rates> rates = ratesServiceDao.selectRatesByMerchant(name);
        String license = parkingInfo.getBusinesslicense();
        String[] license_paths = license.split("&");
        String images = parkingInfo.getMerchantimage();
        String[] images_paths = images.split("&");
        model.addAttribute("merchantdetailedinfo", parkingInfo);
        model.addAttribute("locationdetailedinfo", location);
        model.addAttribute("parkingnumberdetailedinfo", parkingNumbers.get(0));
        model.addAttribute("merchantstatedetailedinfo", merchantStates.get(0));
        model.addAttribute("ratedetailedinfo", rates.get(0));
        model.addAttribute("license_paths", license_paths);
        model.addAttribute("images_paths", images_paths);
        return "auditdetailedinfo";
    }

    @RequestMapping("/selectUncheckChangeMerchantByMerchantname")//通过停车场名查询修改未审核的停车场
    public String selectUncheckChangeMerchantByMerchantname(String name, Model model) {
        ParkingInfo parkingInfo = parkingInfoServiceDao.selectMerchantByMerchantName(name);
        Location location = locationServiceDao.selectParkingByName(name);
        List<Rates> rates = ratesServiceDao.selectRatesByMerchant(name);
        MerchantChange merchantChange = merchantChangeServiceDao.selectMerchatChangeByOldName(name);
        String newlicense = merchantChange.getBusinesslicense();
        String oldlicense = parkingInfo.getBusinesslicense();
        String[] oldlicense_paths = oldlicense.split("&");
        String[] newlicense_paths = newlicense.split("&");
        model.addAttribute("oldmerchantdetailedinfo", parkingInfo);
        model.addAttribute("oldlocationdetailedinfo", location);
        model.addAttribute("oldratedetailedinfo", rates.get(0));
        model.addAttribute("newmerchantdetailedinfo", merchantChange);
        model.addAttribute("ratedetailedinfo", rates.get(0));
        model.addAttribute("oldlicense_paths", oldlicense_paths);
        model.addAttribute("newlicense_paths", newlicense_paths);
        return "changedetailedinfo";
    }

    /**
     * 查询商家详细信息
     *
     * @param name
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectMerchant")
    public String selectMerchant(@Param("name") String name) {
        List<MerchantLoaction> merchantLoactions = parkingInfoServiceDao.selectMerchant(name);
        List<Location> locations = locationServiceDao.selectLocation(name);
        List<ParkingNumber> parkingNumbers = parkingNumberServerDao.selectNumber(name);
        List<MerchantState> merchantStates = merchantStateServiceDao.selectMerchantState(name);
        List<Rates> rates = ratesServiceDao.selectRatesByName(name);
        BaseModel<MerchantProperty> model = new BaseModel<>();
        List<MerchantProperty> properties = new ArrayList<>();
        if (merchantLoactions.size() > 0 && locations.size() > 0 && parkingNumbers.size() > 0) {
            for (int i = 0; i < locations.size(); i++) {
                ParkingInfo parkingInfo = new ParkingInfo();
                parkingInfo.setId(merchantLoactions.get(i).getId());
                parkingInfo.setMerchantname(merchantLoactions.get(i).getMerchantname());
                parkingInfo.setMerchantimage(merchantLoactions.get(i).getMerchantimage());
                parkingInfo.setMerchantaddress(merchantLoactions.get(i).getMerchantaddress());
                parkingInfo.setLinkman(merchantLoactions.get(i).getLinkman());
                parkingInfo.setQQ(merchantLoactions.get(i).getQQ());
                parkingInfo.setBusinesslicense(merchantLoactions.get(i).getBusinesslicense());
                parkingInfo.setUsername(merchantLoactions.get(i).getUsername());
                parkingInfo.setPhone(merchantLoactions.get(i).getPhone());
                MerchantProperty property = new MerchantProperty();
                property.setParkingInfo(parkingInfo);
                property.setLocation(locations.get(i));
                property.setParkingNumber(parkingNumbers.get(i));
                property.setMerchantState(merchantStates.get(i));
                property.setRates(rates.get(i));
                properties.add(property);
            }
            model.setCode(200);
            model.setMessage("查询成功");
            model.setDatas(properties);
            return GsonUtil.GsonString(model);
        } else {
            model.setCode(201);
            model.setMessage("查询失败");
            return GsonUtil.GsonString(model);
        }
    }

    /**
     * 查询车位表
     *
     * @param merchantname
     * @return
     */
    @ResponseBody
    @RequestMapping("/searchParkingSpace")
    public String searchParkingSpace(@Param("merchantname") String merchantname) {
        List<ParkingSpace> parkingSpaces = parkingSpaceServiceDao.searchSpace(merchantname);
        BaseModel<ParkingSpace> model = new BaseModel<>();
        if (parkingSpaces.size() > 0) {
            model.setCode(200);
            model.setMessage("查询成功");
            model.setDatas(parkingSpaces);
            return GsonUtil.GsonString(model);
        } else {
            model.setCode(201);
            model.setMessage("查询失败");
            return GsonUtil.GsonString(model);
        }
    }

    /**
     * 查询商家价格
     *
     * @param merchantname
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectRatesByName")
    public String selectRatesByName(@Param("merchantname") String merchantname) {
        List<Rates> rates = ratesServiceDao.selectRatesByName(merchantname);
        BaseModel<Rates> model = new BaseModel<>();
        if (rates.size() > 0) {
            model.setCode(200);
            model.setMessage("查询成功");
            model.setDatas(rates);
            return GsonUtil.GsonString(model);
        } else {
            model.setCode(201);
            model.setMessage("查询失败");
            return GsonUtil.GsonString(model);
        }
    }

    @RequestMapping("/selectMerchantByKey")//通过关键字查询停车场
    public String selectMerchantByKey(@RequestParam(required = true, defaultValue = "1") int page, String keyword, Model model) {
        keyword = keyword.trim();
        PageHelper.startPage(page, 10);
        List<MerchantLoaction> result = parkingInfoServiceDao.selectMerchantByKey(keyword);
        PageInfo<MerchantLoaction> pageInfo = new PageInfo<>(result);
        model.addAttribute("pagemsg", pageInfo);
        model.addAttribute("merchantinfo", result);
        model.addAttribute("getTotal", pageInfo.getTotal());
        return "merchantinfo";
    }

    @RequestMapping("/selectUncheckMerchantByKey")//通过关键字查询未审核停车场
    public String selectUncheckMerchantByKey(@RequestParam(required = true, defaultValue = "1") int page, String keyword, Model model) {
        keyword = keyword.trim();
        PageHelper.startPage(page, 10);
        List<MerchantLoaction> result = merchantStateServiceDao.selectUncheckMerchant(keyword);
        PageInfo<MerchantLoaction> pageInfo = new PageInfo<>(result);
        model.addAttribute("pagemsg", pageInfo);
        model.addAttribute("merchantinfo", result);
        model.addAttribute("getTotal", pageInfo.getTotal());
        return "auditinfo";
    }

    @RequestMapping("/selectUncheckChangeMerchantByKey")//通过关键字查询修改未审核停车场
    public String selectUncheckChangeMerchantByKey(@RequestParam(required = true, defaultValue = "1") int page, String keyword, Model model) {
        keyword = keyword.trim();
        PageHelper.startPage(page, 10);
        List<MerchantChange> result = merchantStateServiceDao.selectUncheckChangeMerchant(keyword);
        PageInfo<MerchantChange> pageInfo = new PageInfo<>(result);
        model.addAttribute("pagemsg", pageInfo);
        model.addAttribute("merchantinfo", result);
        model.addAttribute("getTotal", pageInfo.getTotal());
        return "changeinfo";
    }

    @ResponseBody
    @RequestMapping("/selectMerchantByUsername")//通过商家名查询名下的停车场
    public String selectMerchantByUsername(@Param("username") String username) {
        List<MerchantLoaction> merchants = parkingInfoServiceDao.selectMerchantByUsername(username);
        List<MerchantProperty> merchantProperties = new ArrayList<>();
        for (int i = 0; i < merchants.size(); i++) {
            MerchantLoaction merchantLoaction = merchants.get(i);
            MerchantProperty property = new MerchantProperty();
            ParkingInfo parkingInfo = new ParkingInfo();
            Location location = new Location();
            MerchantState state = new MerchantState();
            parkingInfo.setId(merchantLoaction.getId());
            parkingInfo.setMerchantname(merchantLoaction.getMerchantname());
            parkingInfo.setUsername(merchantLoaction.getUsername());
            parkingInfo.setBusinesslicense(merchantLoaction.getBusinesslicense());
            parkingInfo.setMerchantimage(merchantLoaction.getMerchantimage());
            parkingInfo.setMerchantaddress(merchantLoaction.getMerchantaddress());
            parkingInfo.setLinkman(merchantLoaction.getLinkman());
            parkingInfo.setPhone(merchantLoaction.getPhone());
            parkingInfo.setQQ(merchantLoaction.getQQ());
            location.setId(merchantLoaction.getId());
            location.setMerchantname(merchantLoaction.getMerchantname());
            location.setCity(merchantLoaction.getCity());
            location.setLongitude(merchantLoaction.getLongitude());
            location.setLatitude(merchantLoaction.getLatitude());
            state.setMerchantname(merchantLoaction.getMerchantname());
            state.setOperatingstate(merchantLoaction.getOperatingstate());
            state.setAuditstate(merchantLoaction.getAuditstate());
            state.setRemark(merchantLoaction.getRemark());
            property.setParkingInfo(parkingInfo);
            property.setLocation(location);
            property.setParkingNumber(null);
            property.setRates(null);
            property.setMerchantState(state);
            merchantProperties.add(property);
        }
        BaseModel<MerchantProperty> model = new BaseModel<>();
        if (merchants.size() > 0) {
            model.setCode(200);
            model.setMessage("查询成功");
            model.setDatas(merchantProperties);
        } else {
            model.setCode(201);
            model.setMessage("查询失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/updateParkingSpaceByNameAndSerialnumber")//通过停车场名和车位号更新车位状态
    public String updateParkingSpaceByNameAndSerialnumber(@RequestBody Map<String, String> spaces) {
        String merchant = spaces.get("merchant");
        Boolean flag = false;
        BaseModel model = new BaseModel();
        ParkingNumber number = new ParkingNumber();
        for (Map.Entry<String, String> m : spaces.entrySet()) {
            if (m.getKey().equals("merchant")) {
                continue;
            }
            String serialnumber = m.getKey();
            String state = m.getValue();
            switch (state) {
                case "不可用":
                    number.setMerchantname(merchant);
                    number.setAllnumber(0);
                    number.setUnusednumber(1);
                    number.setUsednumber(0);
                    number.setSubscribenumber(0);
                    parkingNumberServerDao.updateNumber(number);
                    break;
                case "未使用":
                    number.setMerchantname(merchant);
                    number.setAllnumber(0);
                    number.setUnusednumber(-1);
                    number.setUsednumber(0);
                    number.setSubscribenumber(0);
                    parkingNumberServerDao.updateNumber(number);
                    break;
            }
            int result = parkingSpaceServiceDao.updateParkingSpaceByNameAndSerialnumber(merchant, serialnumber, state);
            if (result > 0) {

            } else {
                flag = true;
                break;
            }
        }
        if (flag) {
            model.setCode(201);
            model.setMessage("更新失败");
        } else {
            model.setCode(200);
            model.setMessage("更新成功");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/updateCheckState")//更新停车场的审核状态
    public String updateCheckState(@RequestBody MerchantState info) {
        MerchantState checkInfo = merchantStateServiceDao.selectCheckInfoByName(info.getMerchantname());
        BaseModel model = new BaseModel();
        switch (checkInfo.getAuditstate()) {
            case "未审核": {
                int result = merchantStateServiceDao.updateCheckInfoByName(info);
                if (result > 0) {
                    model.setCode(200);
                    model.setMessage("数据更新成功");
                } else {
                    model.setCode(201);
                    model.setMessage("数据更新失败");
                }
            }
            break;
            case "未通过": {
                int result = merchantStateServiceDao.updateCheckInfoByName(info);
                if (result > 0) {
                    model.setCode(200);
                    model.setMessage("数据更新成功");
                } else {
                    model.setCode(201);
                    model.setMessage("数据更新失败");
                }
            }
            break;
            default: {
                model.setCode(202);
                model.setMessage("该商家已审核，无需重复操作");
            }
            break;
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/findMerchantState")//通过停车场名查询停车场的状态
    public String findMerchantState(@Param("merchant") String merchant) {
        MerchantState merchantState = merchantStateServiceDao.findMerchantState(merchant);
        BaseModel<MerchantState> model = new BaseModel<>();
        if (merchantState != null) {
            model.setCode(200);
            model.setMessage("查询成功");
            model.setData(merchantState);
        } else {
            model.setCode(201);
            model.setMessage("查询失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/updateParkingState")
    public String updateParkingState(@Param("merchant") String merchant, @Param("state") String state) {
        int result = merchantStateServiceDao.updateParkingState(merchant, state);
        BaseModel model = new BaseModel();
        if (result > 0) {
            model.setCode(200);
            model.setMessage("更新成功");
        } else {
            model.setCode(201);
            model.setMessage("更新失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/updateParikingInfo")
    public String updateParkingInfo(@Param("oldname") String oldname, @Param("auditstate") String auditstate, @Param("remark") String remark) {
        BaseModel model = new BaseModel();
        int result = -1;
        switch (auditstate) {
            case "已通过":
                MerchantChange merchantChange = merchantChangeServiceDao.selectMerchatChangeByOldName(oldname);
                try {
                    FileUtil.rename(oldname, merchantChange.getNewmerchantname());
                    FileUtil.deleteLicenseFile(merchantChange.getNewmerchantname());
                } catch (IOException e) {
                    e.printStackTrace();
                    model.setCode(201);
                    model.setMessage("更新失败");
                    return GsonUtil.GsonString(model);
                }
                String license = merchantChange.getBusinesslicense();
                String[] license_paths = license.split("&");
                for (String license_path : license_paths) {
                    String replace = license_path.replace("/", "\\");
                    try {
                        FileUtil.copy(replace, merchantChange.getNewmerchantname());
                    } catch (IOException e) {
                        e.printStackTrace();
                        model.setCode(201);
                        model.setMessage("更新失败");
                        return GsonUtil.GsonString(model);
                    }
                }
                String path = merchantChange.getBusinesslicense();
                String replace = path.replace("imageChange/" + merchantChange.getOldmerchantname(), "image/" + merchantChange.getNewmerchantname());
                merchantChange.setBusinesslicense(replace);
                merchantStateServiceDao.updateByMerchantChange(merchantChange);
                locationServiceDao.updateByMerchantChange(merchantChange);
                parkingInfoServiceDao.updateByMerchantChange(merchantChange);
                parkingNumberServerDao.updateByMerchantChange(merchantChange);
                parkingSpaceServiceDao.updateByMerchantChange(merchantChange);
                ratesServiceDao.updateByMerchantChange(merchantChange);
                commentServiceDao.updateByMerchantChange(merchantChange);
                result = merchantChangeServiceDao.updateMerchantChangeState(oldname, auditstate, remark);
                break;
            case "未通过":
                result = merchantChangeServiceDao.updateMerchantChangeState(oldname, auditstate, remark);
                break;
        }
        if (result > 0) {
            model.setCode(200);
            model.setMessage("更新成功");
        } else {
            model.setCode(201);
            model.setMessage("更新失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/changeParkingInfo")
    public String changeParkingInfo(@Param("change") String change, @RequestParam("image") MultipartFile[] image) {
        MerchantChange merchantChange = GsonUtil.GsonToBean(change, MerchantChange.class);
        BaseModel model = new BaseModel();
        if (!merchantChange.getOldmerchantname().equals(merchantChange.getNewmerchantname())) {
            ParkingInfo info = parkingInfoServiceDao.selectMerchantByMerchantName(merchantChange.getNewmerchantname());
            if (info != null) {
                model.setCode(202);
                model.setMessage("商家名已存在");
                return GsonUtil.GsonString(model);
            }
        }
        String certificate_paths = "";
        for (int i = 0; i < image.length; i++) {
            String path = null;
            try {
                path = FileUtil.getChangeParkingFilePath(image[i], merchantChange.getNewmerchantname(), "certificate_" + i);
            } catch (IOException e) {
                e.printStackTrace();
                model.setCode(201);
                model.setMessage("文件上传失败");
                return GsonUtil.GsonString(model);
            }
            if (!certificate_paths.equals("")) {
                certificate_paths = certificate_paths + "&" + path;
            } else {
                certificate_paths = certificate_paths + path;
            }
        }
        merchantChange.setBusinesslicense(certificate_paths);
        int result = merchantChangeServiceDao.addMerchantChange(merchantChange);
        if (result > 0) {
            model.setCode(200);
            model.setMessage("插入成功");
        } else {
            model.setCode(201);
            model.setMessage("插入失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/updateMerchantChange")//gai
    public String updateMerchantChange(@Param("change") String change, @RequestParam("image") MultipartFile[] image) {
        MerchantChange merchantChange = GsonUtil.GsonToBean(change, MerchantChange.class);
        BaseModel model = new BaseModel();
        if (!merchantChange.getOldmerchantname().equals(merchantChange.getNewmerchantname())) {
            ParkingInfo info = parkingInfoServiceDao.selectMerchantByMerchantName(merchantChange.getNewmerchantname());
            if (info != null) {
                model.setCode(202);
                model.setMessage("商家名已存在");
                return GsonUtil.GsonString(model);
            }
        }
        String certificate_paths = "";
        try {
            FileUtil.deleteChangeFile(merchantChange.getNewmerchantname());
        } catch (IOException e) {
            e.printStackTrace();
            model.setCode(201);
            model.setMessage("文件上传失败");
            return GsonUtil.GsonString(model);
        }
        for (int i = 0; i < image.length; i++) {
            String path = null;
            try {
                path = FileUtil.getChangeParkingFilePath(image[i], merchantChange.getNewmerchantname(), "certificate_" + i);
            } catch (IOException e) {
                e.printStackTrace();
                model.setCode(201);
                model.setMessage("文件上传失败");
                return GsonUtil.GsonString(model);
            }
            if (!certificate_paths.equals("")) {
                certificate_paths = certificate_paths + "&" + path;
            } else {
                certificate_paths = certificate_paths + path;
            }
        }
        merchantChange.setBusinesslicense(certificate_paths);
        int result = merchantChangeServiceDao.updateMerchantChange(merchantChange);
        if (result > 0) {
            model.setCode(200);
            model.setMessage("更新成功");
        } else {
            model.setCode(201);
            model.setMessage("更新失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/selectMerchantBaseInfo")
    public String selectMerchantBaseInfo(@Param("username") String username) {
        Merchant merchant = merchantServiceDao.selectMerchantByName(username);
        BaseModel<Merchant> model = new BaseModel<>();
        if (merchant != null) {
            model.setCode(200);
            model.setMessage("查询成功");
            model.setData(merchant);
        } else {
            model.setCode(201);
            model.setMessage("查询失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/upadteMerchantAvatar")
    public String updateAvatar(@Param("username") String username, @RequestParam("file") MultipartFile file) {
        BaseModel<String> model = new BaseModel<>();
        try {
            String filePath = FileUtil.getAvatarFilePath(file, username);
            int result = merchantServiceDao.updateAvatar(username, filePath);
            if (result > 0) {
                model.setCode(200);
                model.setMessage("头像上传成功");
                model.setData(filePath);
                return GsonUtil.GsonString(model);
            } else {
                model.setCode(201);
                model.setMessage("头像上传失败");
                return GsonUtil.GsonString(model);
            }
        } catch (IOException e) {
            e.printStackTrace();
            model.setCode(201);
            model.setMessage("头像上传失败");
            return GsonUtil.GsonString(model);
        }
    }

    @ResponseBody
    @RequestMapping("/updateMerchantPhone")
    public String updatePhone(@Param("username") String username, @Param("phone") String phone) {
        int result = merchantServiceDao.updatePhone(username, phone);
        BaseModel<String> model = new BaseModel<>();
        if (result > 0) {
            model.setCode(200);
            model.setMessage("更新成功");
            model.setData(phone);
        } else {
            model.setCode(201);
            model.setMessage("更新失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/isMerchantChange")
    public String isMerchantChange(@Param("oldname") String oldname) {
        MerchantChange merchantChange = merchantChangeServiceDao.selectMerchatChangeByOldName(oldname);
        BaseModel<MerchantChange> model = new BaseModel<>();
        if (merchantChange != null) {
            model.setCode(200);
            model.setMessage("查询成功");
            model.setData(merchantChange);
        } else {
            model.setCode(201);
            model.setMessage("查询失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/selectParkingInfo")
    public String selectParkingInfo(@Param("merchant") String merchant) {
        ParkingInfo parkingInfo = parkingInfoServiceDao.selectMerchantByMerchantName(merchant);
        BaseModel<ParkingInfo> model = new BaseModel<>();
        if (parkingInfo != null) {
            model.setCode(200);
            model.setMessage("查询成功");
            model.setData(parkingInfo);
        } else {
            model.setCode(201);
            model.setMessage("查询失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/updateParkingInfoLink")
    public String updateParkingInfoLink(@Param("merchant") String merchant, @Param("phone") String phone, @Param("linkname") String linkname, @Param("QQ") String QQ) {
        int result = parkingInfoServiceDao.updateParkingInfoLink(merchant, phone, linkname, QQ);
        BaseModel model = new BaseModel();
        if (result > 0) {
            model.setCode(200);
            model.setMessage("更新成功");
        } else {
            model.setCode(201);
            model.setMessage("更新失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/updateMerchantQQ")
    public String updateMerchantQQ(@Param("merchant") String merchant, @Param("QQ") String QQ) {
        int result = merchantServiceDao.updateMerchantQQ(merchant, QQ);
        BaseModel<String> model = new BaseModel<>();
        if (result > 0) {
            model.setCode(200);
            model.setMessage("更新成功");
            model.setData(QQ);
        } else {
            model.setCode(201);
            model.setMessage("更新失败");
        }
        return GsonUtil.GsonString(model);
    }

    @ResponseBody
    @RequestMapping("/isExistParkingInfo")
    public String isExistParkingInfo(@Param("merchantname") String merchantname) {
        ParkingInfo parkingInfo = parkingInfoServiceDao.selectMerchantByMerchantName(merchantname);
        BaseModel<Boolean> model = new BaseModel<>();
        if (parkingInfo == null) {
            model.setCode(201);
            model.setMessage("查询失败");
            model.setData(false);
        } else {
            model.setCode(200);
            model.setMessage("查询成功");
            model.setData(true);
        }
        return GsonUtil.GsonString(model);
    }
}
