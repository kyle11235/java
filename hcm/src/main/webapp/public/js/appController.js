/**
 * Copyright (c) 2014, 2017, Oracle and/or its affiliates.
 * The Universal Permissive License (UPL), Version 1.0
 */
/*
 * Your application specific code will go here
 */
define(['ojs/ojcore', 'knockout', 'appVar', 'viewModels/compView', 'ojs/ojrouter', 'ojs/ojknockout', 'ojs/ojarraytabledatasource',
    'ojs/ojoffcanvas', 'ojs/ojdialog', 'ojs/ojselectcombobox'],
        function (oj, ko, gVar, compView) {
            function ControllerViewModel() {
                var self = this;
                self.selTalentPoolId = ko.observable("");
                self.selTalentPoolName = ko.observable();
                self.newTalentPoolId = ko.observable();
                self.newTalentPoolName = ko.observable();
                // Media queries for repsonsive layouts
                var smQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.SM_ONLY);
                self.smScreen = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(smQuery);
                var mdQuery = oj.ResponsiveUtils.getFrameworkQuery(oj.ResponsiveUtils.FRAMEWORK_QUERY_KEY.MD_UP);
                self.mdScreen = oj.ResponsiveKnockoutUtils.createMediaQueryObservable(mdQuery);

                // Router setup
                self.router = oj.Router.rootInstance;
                self.router.configure({
                    'Home': {label: 'Home', isDefault: true},
                    'dashboard': {label: 'Dashboard'},
                    'searchList': {label: 'searchList'},
                    'compView': {label: 'compView'},
                    'empDetail': {label: 'empDetail'}
                }
                );
                oj.Router.defaults['urlAdapter'] = new oj.Router.urlParamAdapter();

                // Navigation setup
                var navData = [
                    {name: 'Home', id: 'Home',
                        iconClass: 'oj-navigationlist-item-icon demo-icon-font-24 demo-chart-icon-24'},
                    {name: 'Incidents', id: 'incidents',
                        iconClass: 'oj-navigationlist-item-icon demo-icon-font-24 demo-fire-icon-24'},
                    {name: 'Customers', id: 'customers',
                        iconClass: 'oj-navigationlist-item-icon demo-icon-font-24 demo-people-icon-24'},
                    {name: 'About', id: 'about',
                        iconClass: 'oj-navigationlist-item-icon demo-icon-font-24 demo-info-icon-24'}
                ];
                self.navDataSource = new oj.ArrayTableDataSource(navData, {idAttribute: 'id'});

                self.update_buttonDisplay = ko.computed(function () {
                    switch (self.router.stateId()) {
                        case 'Home':
                            return false;
                            break;
                        case 'compView':
                            return true;
                            break;
                        case 'searchList':
                            return false;
                            break;
                        case 'empDetail':
                            return false;
                            break;
                    }
                });

                self.comp_buttonDisplay = ko.computed(function () {
                    switch (self.router.stateId()) {
                        case 'Home':
                            return false;
                            break;
                        case 'compView':
                            return false;
                            break;
                        case 'searchList':
                            return true;
                            break;
                        case 'empDetail':
                            return false;
                            break;
                    }
                });

                self.talent_Display = ko.computed(function () {
                    switch (self.router.stateId()) {
                        case 'Home':
                            return false;
                            break;
                        case 'compView':
                            return false;
                            break;
                        case 'searchList':
                            return true;
                            break;
                        case 'empDetail':
                            return false;
                            break;
                    }
                });

                // Drawer
                // Close offcanvas on medium and larger screens
                self.mdScreen.subscribe(function () {
                    oj.OffcanvasUtils.close(self.drawerParams);
                });
                self.drawerParams = {
                    displayMode: 'push',
                    selector: '#navDrawer',
                    content: '#pageContent'
                };
                // Called by navigation drawer toggle button and after selection of nav drawer item
                self.toggleDrawer = function () {
                    return oj.OffcanvasUtils.toggle(self.drawerParams);
                }
                // Add a close listener so we can move focus back to the toggle button when the drawer closes
                $("#navDrawer").on("ojclose", function () {
                    $('#drawerToggleButton').focus();
                });

                // Header
                // Application Name used in Branding Area
                self.appName = ko.observable("HCMApp");
                // User Info used in Global Navigation area
                self.userLogin = ko.observable("john.hancock@oracle.com");

                self.talentPoolOptions = ko.observableArray([{value: '-1', label: ''},
                    {value: 'ID001', label: 'Talent Pool A'},
                    {value: 'ID002', label: 'Talent Pool B'},
                    {value: 'ID003', label: 'Talent Pool C'}]);


                self.gotoPreviousView = function () {
                    switch (self.router.stateId()) {
                        case 'Home':
                            oj.Router.rootInstance.go('Home');
                            return true;
                            break;
                        case 'compView':
                            oj.Router.rootInstance.go('searchList');
                            return true;
                            break;
                        case 'searchList':
                            oj.Router.rootInstance.go('Home');
                            return true;
                            break;
                        case 'empDetail':
                            oj.Router.rootInstance.go('compView');
                            return true;
                            break;
                    }
                };
                
                self.careerButtonClick = function () {
                	var url = "http://" + window.location.host + "/mitsubishi/authorization/personcareer/goRegist/";
                	var selected = false;
                	if (gVar.listData.employees) {
                		gVar.listData.employees.forEach(function (val, index, arr) {
                			if (arr[index].checkValue()[0] === "1") {
                				selected = true;
                				console.log(arr[index].person_number);
                				window.open(url + arr[index].person_number);
                			}
                		});
					}
                    if(!selected){
                    	window.open("http://" + window.location.host + "/mitsubishi/menu");
                    }
                };

                self.compareButtonClick = function () {

                    gVar.IDs = new Array();

                    gVar.listData.employees.forEach(function (val, index, arr) {
                        if (arr[index].checkValue()[0] === "1") {
                            gVar.IDs.push(arr[index].person_number);
                        }
                    });


                    if (gVar.IDs.length > 4) {
                        alert("Please select 1-4 employee to compare");
                    } else {
                        self.getCompareData(gVar.IDs);
                    }
                };


                self.updateButtonClick = function () {

                    gVar.printPeople = new Array();

                    for (var l = 0; l < gVar.compareArray.length; l++) {
                        var input = document.getElementById('emp' + gVar.compareArray[l].person_number.toString());
                        var isChecked = input.checked;
                        isChecked = (isChecked) ? "checked" : "not checked";
                        if (isChecked === "checked") {
                            gVar.printPeople.push(gVar.compareArray[l]);
                        }
                    }

                    if (undefined !== gVar.printPeople[0]) {
                        window.open("https://xxx/analytics/saw.dll?PortalGo&Action=prompt&path=%2Fshared%2FCustom%2F%5BJD%5D%2FCareerInfo_All");
                        oj.Router.rootInstance.go('empDetail');
                    }

                };

                self.getCompareData = function (ids) {
                    var newIDs = ids[0] + "";
                    for (var i = 1; i < ids.length; i++) {
                        newIDs += ',' + ids[i];
                    }
                    ;
                    gVar.compareArray = new Array();
                    var ServiceURL = gVar.ServiceURL;
                    $.ajax({
                        type: "GET",
                        url: ServiceURL + "?ids=" + newIDs,
                        cache: false,
                        success: function (data) {
                            gVar.compareArray = data.employees;
                            oj.Router.rootInstance.go('compView');
                        },
                        error: function (err) {
                            alert(err.statusText);
                        }
                    });
                };


                self.getPosition = function ()
                {
                    return {'my': {'horizontal': 'left',
                            'vertical': 'top'
                        },
                        'at': {'horizontal': 'end',
                            'vertical': 'bottom'
                        },
                        'collision': 'none'};
                };

                self.addNewTalentPool = function ()
                {
                    var noEmpsToAdd = $("input:checked").length;
//                    if (noEmpsToAdd == 0)
//                    {
//                        alert('Error. Kindly select employees to be added')
//                        $("#dialogWithUserDefinedHeader").ojDialog("close");
//                        return false;
//                    }
//                    var existingTalentPools = self.talentPoolOptions();
//                    $.each(existingTalentPools, function (index) {
//                        if (existingTalentPools[index]['value'] === self.newTalentPoolId()) {
//                            alert("Talent Pool Already Exists");
//                            return false;
//                        } else if (index == existingTalentPools.length - 1) {
//                            self.talentPoolOptions.push({value: self.newTalentPoolId(), label: self.newTalentPoolName()});
//                        }
//                    });
                    self.talentPoolOptions.push({value: self.newTalentPoolId(), label: self.newTalentPoolName()});
                    $("#dialogWithUserDefinedHeader").ojDialog("close");
                };

                self.updateTalentPool = function ()
                {
                    alert("既存人材プールに従業員が追加されました。");
                };

                self.deleteTalentPool = function ()
                {
                    if (self.selTalentPoolId() != "-1") {
                        self.talentPoolOptions.remove(function (current) {
                            return (current.value == self.selTalentPoolId());
                        });
                        self.selTalentPoolId("");
                    }
                }
                // Footer
                function footerLink(name, id, linkTarget) {
                    this.name = name;
                    this.linkId = id;
                    this.linkTarget = linkTarget;
                }
                self.footerLinks = ko.observableArray([
                    new footerLink('About Oracle', 'aboutOracle', 'http://xxx/us/corporate/index.html#menu-about'),
                    new footerLink('Contact Us', 'contactUs', 'http://xxx/us/corporate/contact/index.html'),
                    new footerLink('Legal Notices', 'legalNotices', 'http://xxx/us/legal/index.html'),
                    new footerLink('Terms Of Use', 'termsOfUse', 'http://xxx/us/legal/terms/index.html'),
                    new footerLink('Your Privacy Rights', 'yourPrivacyRights', 'http://xxx/us/legal/privacy/index.html')
                ]);
            }

            $(
                    function () {
                        $("#cancelButton").click(function () {
                            $("#dialogWithUserDefinedHeader").ojDialog("close");
                        });
                        $("#newtpl").click(function () {
                            $("#dialogWithUserDefinedHeader").ojDialog("open");
                            $('#talentPoolPopup').ojPopup("close");
                        });
                    }
            );
            
            self.downloadButtonClick = function () {
            	var current = 0;
            	var total = 7;
            	$("#downloadDia").ojDialog("open");
            	self.x = setInterval(function() {
            		  var progress = Math.round((++current/total)*100);
            		  $("#dialogTitleId").html('処理中' + progress + '%');
	        		  if (current >= total) {
	        		    clearInterval(self.x);
	        		    $("#downloadDia").ojDialog("close");
	        		    $("#dialogTitleId").html('処理中');
		            	var url = "download.xlsx";
		            	window.open(url);
	        		  }
        		}, 1000);
            };
            
            $(
                    function () {
                        $("#cancelDownloadButton").click(function () {
                            $("#downloadDia").ojDialog("close");
                            clearInterval(self.x);
                            $("#dialogTitleId").html('処理中');
                        });
                    }
            );
            
            return new ControllerViewModel();
        }
);
