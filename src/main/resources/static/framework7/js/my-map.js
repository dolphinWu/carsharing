var map = new AMap.Map('container', {
    resizeEnable: true, //是否监控地图容器尺寸变化
    zoom: 10, //初始化地图层级
    center: currentPosition //初始化地图中心点
});

AMap.plugin('AMap.Geolocation', function () {
    var geolocation = new AMap.Geolocation({
        enableHighAccuracy: true,//是否使用高精度定位，默认:true
        timeout: 10000,          //超过10秒后停止定位，默认：5s
        buttonPosition: 'RB',    //定位按钮的停靠位置
        buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
        zoomToAccuracy: true  //定位成功后是否自动调整地图视野到定位点

    });
    map.addControl(geolocation);
    geolocation.getCurrentPosition(function (status, result) {
        if (status == 'complete') {
            onComplete(result)
        } else {
            onError(result)
        }
    });
});

//解析定位结果
function onComplete(data) {
}

//解析定位错误信息
function onError(data) {
}

AMapUI.loadUI(['misc/PoiPicker'], function (PoiPicker) {

    var poiPicker = new PoiPicker({
        input: 'pickerInput'
    });

    //初始化poiPicker
    poiPickerReady(poiPicker);
});

function poiPickerReady(poiPicker) {
    window.poiPicker = poiPicker;
    var marker = new AMap.Marker();

    var infoWindow = new AMap.InfoWindow({
        offset: new AMap.Pixel(0, -20)
    });

    //选取了某个POI
    poiPicker.on('poiPicked', function (poiResult) {
        var source = poiResult.source,
            poi = poiResult.item,
            info = {
                source: source,
                id: poi.id,
                name: poi.name,
                location: poi.location.toString(),
                address: poi.address
            };

        marker.setMap(map);
        infoWindow.setMap(map);

        marker.setPosition(poi.location);
        infoWindow.setPosition(poi.location);

        infoWindow.setContent('POI信息: <pre>' + JSON.stringify(info, null, 2) + '</pre>');
        infoWindow.open(map, marker.getPosition());

        map.setCenter(marker.getPosition());
    });
}