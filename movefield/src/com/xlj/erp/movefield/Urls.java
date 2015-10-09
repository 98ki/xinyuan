package com.xlj.erp.movefield;

import android.content.Context;

import com.xlj.erp.movefield.utils.PrefsUtils;

/**
 * 网络请求URL
 * 
 * @author chaohui.yang
 *
 */
public class Urls {
	public static String SERVER_HOST = "http://59.108.51.122";

	/**
	 * 登录接口zhg,123
	 */
	public static String URL_LOGIN = "/appServer/login.action?username=%s&password=%s";
	/**
	 * 获取LOGO
	 */
	public static String URL_GET_LOGO = "/appServer/getCompanyLogo.action";
	/**
	 * 首页轮播图
	 */
	public static String URL_GET_CAROUSEL_PIC = "/appServer/getCarouselPicByProjectId?projectId=%s";
	/**
	 * 待办和催办 （backlog）
	 */
	public static String URL_GET_BUSINESS_INFO = "/appServer/getBusinessInfo.action?projectId=%s&userid=%s&username=%s&type=%s";

	/**
	 * 楼盘信息
	 */
	public static String URL_BUILDING_INFO = "/appServer/getBuildInfoByProjectId.action?projectId=%s&username=%s";

	/**
	 * 效果图
	 */
	public static String URL_DESIGN_SKETCH = "/appServer/getAllPicUrlByProjectId.action?projectId=%s";
	/**
	 * 楼栋列表
	 */
	public static String URL_GET_BULIDINGS = "/appServer/getBulidingsByProjectId.action?projectId=%s&username=%s";
	/**
	 * 试算价格
	 */
	public static String URL_MORTGAGE_CALC = "/appServer/muiLib/page/calcPrice.jsp?totalPrice=%s";
	/**
	 * 项目资料
	 */
	public static String URL_GET_PROJECT_FILE = "/appServer/getSalesDocByProjectId.action?projectId=%s";
	/**
	 * 主推户型
	 */
	public static String URL_GET_PROMOTE_ROOM = "/appServer/getToPromoteHouseByPId.action?projectId=%s";
	/**
	 * 房屋查询
	 */
	public static String URL_GET_HOUSE_BY_BUILDING_ID = "/appServer/getHouseByBuildingId.action?username=%s&projectId=%s&buildingId=%s&unitName=%s&roomType=%s&roomArea=%s&startFloor=%s&endFloor=%s&minPrice=%s&maxPrice=%s";
	/**
	 * 房屋查询筛选条件：单元
	 */
	public static String URL_GET_UNITS_BY_BUILDING_ID = "/appServer/getUnitsByBuildingId.action?buildingId=%s";
	/**
	 * 房屋查询筛选条件：户型
	 */
	public static String URL_GET_ROOM_TYPES_BY_UNIT = "/appServer/getRoomTypesByUnit.action?buildingId=%s&unitName=%s";
	/**
	 * 房屋查询筛选条件：面积
	 */
	public static String URL_GET_ROOM_AREA_BY_UNIT_OR_RTYPE = "/appServer/getRoomAreaByUnitOrRType.action?buildingId=%s&roomType=%s&unitName=%s";
	/**
	 * 房屋查询筛选条件：楼层
	 */
	public static String URL_GET_ALL_FLOOR_BY_BUILDING_ID = "/appServer/getAllFloorByBuildingId.action?buildingId=%s";
	/**
	 * 销售分析
	 */
	public static String URL_GET_SALEANALYSIS = "/appServer/getSalesAnalysis.action?projectId=%s&userid=%s&username=%s";

	/**
	 * 案场监控首页URL_GET_FIELDMONITOR
	 */
	public static String URL_GET_FIELDMONITOR = "/appServer/getFieldMonitoring.action?projectId=%s&userid=%s&username=%s";
	/**
	 * 案场监控销售分析
	 */
	public static String URL_GET_MONITORSALEANALYSIS = "/appServer/getMonitorSalesAnalysis.action?projectId=%s&userid=%s";
	/**
	 * 客户分析-认知途径等
	 */
	public static String URL_GET_CUSTOMERANALYSISKNOWWAY = "/appServer/getCustomerIntentionAnalysis1.action?projectId=%s&userid=%s";
	/**
	 * 客户分析-新客户等
	 */
	public static String URL_GET_CUSTOMERANALYSISNEW = "/appServer/getCustomerIntentionAnalysis2.action?projectId=%s&userid=%s";

	/**
	 * 客户列表
	 */
	public static String URL_GET_MY_CUSTOMER_LIST = "/appServer/getMyCustomerList.action?projectId=%s&userid=%s&username=%s&cusAge=%s&cusInterest=%s&cusStatus=%s&cusName=%s&cusPhone=%s&cusCondition=%s";

	/**
	 * 客户基本信息
	 */
	public static String URL_GET_ONE_CUSTOMER_BY_ID = "/appServer/getOneCustomerById.action?projectId=%s&userid=%s&username=%s&customerid=%s";
	/**
	 * 跟进记录
	 */
	public static String URL_GET_FOLLOW_RECORD = "/appServer/getFollowRecord.action?projectId=%s&userid=%s&username=%s&customerid=%s";
	/**
	 * 销售记录
	 */
	public static String URL_GET_SALES_RECORD = "/appServer/getSalesRecord.action?projectId=%s&userid=%s&username=%s&customerid=%s";

	/**
	 * 客户意向(key-value)
	 */
	public static String URL_GET_INTEREST_DGREE = "/appServer/getInterestDgree.action?projectId=%s&userid=%s&username=%s";
	/**
	 * 年龄段(字符串列表)
	 */
	public static String URL_GET_VAGERANGE = "/appServer/getVagerange.action?projectId=%s&userid=%s&username=%s";
	/**
	 * 意向房源(key-value)
	 */
	public static String URL_GET_INTEREST_HOUSE = "/appServer/getInterestHouse.action?projectId=%s&userid=%s&username=%s";
	/**
	 * 认知媒体(key-value)
	 */
	public static String URL_GET_MTZL = "/appServer/getMTZL.action?projectId=%s&userid=%s&username=%s";
	/**
	 * 客户来源(key-value)
	 */
	public static String URL_GET_CUSTOMERRE_SOURCE = "/appServer/getCustomerResource.action?projectId=%s&userid=%s&username=%s";
	/**
	 * 客户状态(字符串列表)
	 */
	public static String URL_GET_CUST_STATUS = "/appServer/getCustStatus.action?projectId=%s&userid=%s&username=%s";
	/**
	 * 跟进方式(key-value)
	 */
	public static String URL_GET_GJFS = "/appServer/getGJFS.action?projectId=%s&userid=%s&username=%s";

	/**
	 * 增加跟进记录
	 */
	public static String URL_ADD_ONE_FOLLOW_RECORD = "/appServer/addOneFollowRecord.action?projectId=%s&userid=%s&username=%s&customerid=%s&followtypeid=%s&followtime=%s&followcontent=%s&nextfollowdate=%s";

	/**
	 * 案场监控回款分析
	 */
	public static String URL_GET_MONITORBACKPAYMENTANALYSIS = "/appServer/getReturnedMoneyAnalysis.action?projectId=%s&userid=%s";
	/**
	 * 新增客户
	 */
	public static String URL_ADD_ONE_CUSTOMER = "/appServer/addOneCustomer.action?projectId=%s&userid=%s&username=%s&customername=%s&customersex=%s&interestdegreeid=%s&phone=%s&interesthousetypeid=%s&agebracket=%s&mediaid=%s&customersourceid=%s&nextfollowdate=%s&comment=%s";
	/**
	 * 客户跟进-逾期跟进
	 */
	public static String URL_GET_YQGJ_CUSTOMER = "/appServer/getYQGJCustomer.action?projectId=%s&userid=%s&username=%s";
	/**
	 * 客户跟进-销售线索
	 */
	public static String URL_GET_XSXS_CUSTOMER = "/appServer/getXSXSCustomer.action?projectId=%s&userid=%s&username=%s";
	/**
	 * 置业经理列表
	 */
	public static String URL_GET_ESTATE_MANAGER = "/appServer/getEstateManager.action?projectId=%s&userid=%s&username=%s&managerName=%s";
	/**
	 * 客户分配给经理
	 */
	public static String URL_DISTRIBUTION_CUSTOMER = "/appServer/distributionCustomer.action?projectId=%s&userid=%s&username=%s&customerIds=%s&managerId=%s&flag=%s";
	/**
	 * 判断有无新版本
	 */
	public static String URL_UPDATE_APP_VERSION = "/appServer/updateAppVersion.action?apptype=android&appversion=%s";

	public static String getLoginUrl() {
		return SERVER_HOST + URL_LOGIN;
	}

	public static String getLogo() {
		return SERVER_HOST + URL_GET_LOGO;
	}

	public static String getCarouselPic() {
		return SERVER_HOST + URL_GET_CAROUSEL_PIC;
	}

	public static String getBuildingInfo() {
		return SERVER_HOST + URL_BUILDING_INFO;
	}

	public static String getDesignSketch() {
		return SERVER_HOST + URL_DESIGN_SKETCH;
	}

	public static String getBulidingsByProjectId() {
		return SERVER_HOST + URL_GET_BULIDINGS;
	}

	public static String getProjectFileByProjectId() {
		return SERVER_HOST + URL_GET_PROJECT_FILE;
	}

	public static String getMortgageCalc() {
		return SERVER_HOST + URL_MORTGAGE_CALC;
	}

	public static String getPromoteRoom() {
		return SERVER_HOST + URL_GET_PROMOTE_ROOM;
	}

	/**
	 * Backlog
	 */
	public static String getBusinessInfo() {
		return SERVER_HOST + URL_GET_BUSINESS_INFO;
	}

	/**
	 * 获取案场监控销售分析数据
	 * 
	 * @return
	 */
	public static String getMonitorSaleAnalysis() {
		return SERVER_HOST + URL_GET_MONITORSALEANALYSIS;
	}

	/**
	 * 获取案场监控回款分析数据
	 * 
	 * @return
	 */
	public static String getMonitorBackPaymentAnalysis() {
		return SERVER_HOST + URL_GET_MONITORBACKPAYMENTANALYSIS;
	}

	/**
	 * 客户分析-认知途径等数据
	 * 
	 * @return
	 */
	public static String getCusttomerAnalysisKnowWay() {
		return SERVER_HOST + URL_GET_CUSTOMERANALYSISKNOWWAY;
	}

	/**
	 * 客户分析-认知途径等数据新客户等数据
	 * 
	 * @return
	 */
	public static String getCusttomerAnalysisNew() {
		return SERVER_HOST + URL_GET_CUSTOMERANALYSISNEW;
	}

	/**
	 * 获取销售分析数据
	 * 
	 * @return
	 */
	public static String getSaleAnalysis() {
		return SERVER_HOST + URL_GET_SALEANALYSIS;
	}

	/**
	 * 获取案场监控首页数据
	 * 
	 * @return
	 */
	public static String getFieldMonitor() {
		return SERVER_HOST + URL_GET_FIELDMONITOR;
	}

	public static String getHouseByBuildingId() {
		return SERVER_HOST + URL_GET_HOUSE_BY_BUILDING_ID;
	}

	public static String getUnitsByBuildingId() {
		return SERVER_HOST + URL_GET_UNITS_BY_BUILDING_ID;
	}

	public static String getRoomTypesByUnit() {
		return SERVER_HOST + URL_GET_ROOM_TYPES_BY_UNIT;
	}

	public static String getRoomAreaByUnitOrRType() {
		return SERVER_HOST + URL_GET_ROOM_AREA_BY_UNIT_OR_RTYPE;
	}

	public static String getAllFloorByBuildingId() {
		return SERVER_HOST + URL_GET_ALL_FLOOR_BY_BUILDING_ID;
	}

	public static String addOneCustomer() {
		return SERVER_HOST + URL_ADD_ONE_CUSTOMER;
	}

	/**
	 * 客户意向(key-value)
	 */
	public static String getInterestDgree() {
		return SERVER_HOST + URL_GET_INTEREST_DGREE;
	}

	/**
	 * 年龄段(字符串列表)
	 */
	public static String getVagerange() {
		return SERVER_HOST + URL_GET_VAGERANGE;
	}

	/**
	 * 意向房源(key-value)
	 */
	public static String getInterestHouse() {
		return SERVER_HOST + URL_GET_INTEREST_HOUSE;
	}

	/**
	 * 认知媒体(key-value)
	 */
	public static String getMTZL() {
		return SERVER_HOST + URL_GET_MTZL;
	}

	/**
	 * 客户来源(key-value)
	 */
	public static String getCustomerResource() {
		return SERVER_HOST + URL_GET_CUSTOMERRE_SOURCE;
	}

	/**
	 * 客户状态(字符串列表)
	 */
	public static String getCustStatus() {
		return SERVER_HOST + URL_GET_CUST_STATUS;
	}

	/**
	 * 跟进方式(key-value)
	 */
	public static String getGJFS() {
		return SERVER_HOST + URL_GET_GJFS;
	}

	public static String addOneFollowRecord() {
		return SERVER_HOST + URL_ADD_ONE_FOLLOW_RECORD;
	}

	public static String getYQGJCustomer() {
		return SERVER_HOST + URL_GET_YQGJ_CUSTOMER;
	}

	public static String getXSXSCustomer() {
		return SERVER_HOST + URL_GET_XSXS_CUSTOMER;
	}

	public static String getEstateManager() {
		return SERVER_HOST + URL_GET_ESTATE_MANAGER;
	}

	public static String distributionCustomer() {
		return SERVER_HOST + URL_DISTRIBUTION_CUSTOMER;
	}

	public static String getMyCustomerList() {
		return SERVER_HOST + URL_GET_MY_CUSTOMER_LIST;
	}

	public static String getOneCustomerById() {
		return SERVER_HOST + URL_GET_ONE_CUSTOMER_BY_ID;
	}

	public static String getFollowRecord() {
		return SERVER_HOST + URL_GET_FOLLOW_RECORD;
	}

	public static String getSalesRecord() {
		return SERVER_HOST + URL_GET_SALES_RECORD;
	}
	
	public static String updateAppVersion(){
		return SERVER_HOST + URL_UPDATE_APP_VERSION;
	}

	/**
	 * 运行程序时调用
	 * 
	 * @param context
	 */
	public static void initServerHost(Context context) {
		SERVER_HOST = PrefsUtils.getString(context, PrefsUtils.KEY_SERVER_HOST, SERVER_HOST);
	}

	/**
	 * 设置服务器地址
	 * 
	 * @param context
	 * @param serverHost
	 */
	public static void setServerHost(Context context, String serverHost) {
		if (serverHost.endsWith("/")) {
			serverHost = serverHost.substring(0, serverHost.length() - 1);
		}
		SERVER_HOST = serverHost;
		PrefsUtils.putString(context, PrefsUtils.KEY_SERVER_HOST, serverHost);
	}

	/**
	 * 获取服务器地址
	 * 
	 * @return
	 */
	public static String getServerHost(Context context) {
		return PrefsUtils.getString(context, PrefsUtils.KEY_SERVER_HOST, SERVER_HOST);
	}
}
