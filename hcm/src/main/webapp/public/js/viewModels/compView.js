/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * compView module
 */
define(['ojs/ojcore', 'knockout', 'jquery', 'appVar', 'ojs/ojknockout', 'promise', 'ojs/ojlistview', 'ojs/ojarraytabledatasource', 'ojs/ojbutton', 'ojs/ojcheckboxset', 'ojs/ojradioset'
], function (oj, ko, $, gVar) {
    /**
     * The view model for the main content view template
     */
    function compViewContentViewModel() {
        var self = this;
        // if the contents of the array can change, then replace the [...] with ko.observableArray([...])
        self.empRadios = [
            {id: 'BI', label: '基本情報'},
            {id: 'WE', label: '業務経験情報'},
            {id: 'EH', label: '人事評価情報'}
        ];

        var printPeople = new Array();
        // observable bound to the Buttonset:
        self.radioBtn = ko.observable("BI");

        self.checkValue = ko.observableArray(["0"]);

        var tempSelectedArray = new Array();
        tempSelectedArray = gVar.compareArray;
        self.newHtml = ko.observable('');
        var newHtmlText = '';

        self.initView = function (array) {
            for (var i = 0; i < array.length; i++) {
                self.bindHtml(array[i]);
            }
            self.newHtml(newHtmlText);
        };

        self.getByteLen = function (val) {
            var len = 0;
            for (var i = 0; i < val.length; i++) {
                if (val[i].match(/[^x00-xff]/ig) != null) //全角
                    len += 2;
                else
                    len += 1;
            }
            return len;
        };

        self.bindHtml = function (obj) {
//            ' + obj.grade_name + '
            var BIAC = "";
            var BITH = "";

            if (undefined !== obj.assignments[0]) {
                for (var k = 0; k < obj.assignments.length; k++) {
                    var singleLine = '<p>' + obj.assignments[k].start_date + '&nbsp&nbsp' + obj.assignments[k].department + '</p>';
                    BIAC += singleLine;
                }
            }


            if (undefined !== obj.trainings[0]) {
                for (var k = 0; k < obj.trainings.length; k++) {
                    if (obj.trainings[k].end_date === null) {
                        obj.trainings[k].end_date = "now";
                    }

                    var singleLine = '<p>' + obj.trainings[k].start_date + '-' + obj.trainings[k].end_date + '&nbsp&nbsp' + obj.trainings[k].training_course + '</p>';
                    BITH += singleLine;
                }
            }

            var WEPM = "";
            var WEJF = "";
            var WELo = "";

            if (undefined !== obj.profiles[0]) {
                for (var k = 0; k < obj.profiles.length; k++) {
                    if (obj.profiles[k].product_end_date === null) {
                        obj.profiles[k].product_end_date = "now";
                    }
                    if (obj.profiles[k].func_end_date === null) {
                        obj.profiles[k].func_end_date = "now";
                    }
                    if (obj.profiles[k].loc_end_date === null) {
                        obj.profiles[k].loc_end_date = "now";
                    }



                    var singlePMLine = '<p>' + obj.profiles[k].product_start_date + '-' + obj.profiles[k].product_end_date + '&nbsp&nbsp' + obj.profiles[k].product + '(' + obj.profiles[k].role + ')' + '</p>';
                    var singleJFLine = '<p>' + obj.profiles[k].func_start_date + '-' + obj.profiles[k].func_end_date + '&nbsp&nbsp' + obj.profiles[k].function + '</p>';
                    var singlelocLine = '<p>' + obj.profiles[k].loc_start_date + '-' + obj.profiles[k].loc_end_date + '&nbsp&nbsp' + obj.profiles[k].city + '</p>';
                    WEPM += singlePMLine;
                    WEJF += singleJFLine;
                    WELo += singlelocLine;
                }
            }

            var alink = "";
            var proImage = "";

            if (obj.person_number === 126) {
                proImage = "css/images/person/" + obj.person_number.toString() + ".png";
                alink = "https://ucf5-fap1082-bi.oracledemos.com/analytics/saw.dll?PortalGo&Action=prompt&path=%2Fshared%2FCustom%2F%5BJD%5D%2FCareerInfo_1";
            } else if (obj.person_number === 131) {
                proImage = "css/images/person/" + obj.person_number.toString() + ".png";
                alink = "https://ucf5-fap1082-bi.oracledemos.com/analytics/saw.dll?PortalGo&Action=prompt&path=%2Fshared%2FCustom%2F%5BJD%5D%2FCareerInfo_2";
            } else if (obj.person_number === 141) {
                proImage = "css/images/person/" + obj.person_number.toString() + ".png";
                alink = "https://ucf5-fap1082-bi.oracledemos.com/analytics/saw.dll?PortalGo&Action=prompt&path=%2Fshared%2FCustom%2F%5BJD%5D%2FCareerInfo_3";
            } else {
                alink = "https://ucf5-fap1082-bi.oracledemos.com/analytics/";
                proImage = "css/images/person/default.png";
            }

            var EHo = "";
            var EHen = "";

            if (undefined !== obj.evaluations[0]) {
                for (var k = 0; k < obj.evaluations.length; k++) {
                    var ratingString = obj.evaluations[k].rating;
                    var singleEHLine = '<p>' + obj.evaluations[k].performance_document + '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp' + obj.evaluations[k].rating + '</p>';
                    if (self.getByteLen(ratingString) === 2) {
                        EHo += singleEHLine;
                    } else {
                        EHen += singleEHLine;
                    }
                }
            }

            var tempHtmlCell = '<div class="oj-flex-item">'
                    + '<div class="com_profile">'
                    + '<div class="spanIt"><img class="imageStyle" src ="' + proImage + '"/></div>'
                    + '<div class="spanIt leftIt">'
                    + '<p><span>' + obj.department + '</span></p>'
                    + '<p><span>氏名 : </span><a href="' + alink + '" target="_blank" style="color: #0A6DB0" class="pro_value"> ' + obj.name + '</a></p>'
                    + '<p><span>キャリアバンド : </span><span style="color: #4F4F4F" class="pro_value"> ' + obj.grade_name + '</span></p>'
                    + '<p><span>職責区分 : </span><span style="color: #4F4F4F" class="pro_value"> ' + obj.grade_step_name + '</span></p>'
                    + '</div>'
                    + '<div class="rightIt" style="position: relative;height: 35px;">'
                    + '<form action="#">'
                    + '<div>'
                    + '<input type="checkbox" id="' + 'emp' + obj.person_number + '"/>'
                    + '<label for="' + 'emp' + obj.person_number + '"></label>'
                    + '</div>'
                    + '</form>'
                    + '</div>'
                    + '</div>'
                    + '<div class="spacer">'
                    + '</div>'
                    + '<div class="BIinfo com_compDetail"> '
                    + '<div class="spanIt leftIt2">'
                    + '<div class="t1">'
                    + '<div class="detailArea style-2 scrollbar">'
                    + BIAC
                    + '</div>'
                    + '</div>'
                    + '<div class="t1">'
                    + '<div class="detailArea style-2 scrollbar">'
                    + BITH
                    + '</div>'
                    + '</div>'
                    + '<div class="t1">'
                    + '<div class="comArea">'
                    + '<p class="subTitle">本人キャリア希望</p>'
                    + '<input type="text" disabled="disabled" value="' + obj.emp_career_plan + '" />'
                    + '<p class="subTitle">上司所見</p>'
                    + '<input type="text" disabled="disabled" value="' + obj.manager_comment + '"/>'
                    + '</div>'
                    + '</div>'
                    + '</div>'
                    + '</div>'
                    + '<div class="WEinfo com_compDetail"> '
                    + '<div class="spanIt leftIt2">'
                    + '<div class="t1">'
                    + '<div class="detailArea style-2 scrollbar">'
                    + WEPM
                    + '</div>'
                    + '</div>'
                    + '<div class="t1">'
                    + '<div class="detailArea style-2 scrollbar">'
                    + WEJF
                    + '</div>'
                    + '</div>'
                    + '<div class="t1">'
                    + '<div class="detailArea style-2 scrollbar">'
                    + WELo
                    + '</div>'
                    + '</div>'
                    + '</div>'
                    + '</div>'
                    + '<div class="EHinfo com_compDetail"> '
                    + '<div class="spanIt leftIt2">'
                    + '<div class="t1">'
                    + '<div class="detailArea style-2 scrollbar">'
                    + EHo
                    + '</div>'
                    + '</div>'
                    + '<div class="t1">'
                    + '<div class="detailArea style-2 scrollbar">'
                    + EHen
                    + '</div>'
                    + '</div>'
                    + '</div>'
                    + '</div>'
                    + '</div>';

            newHtmlText += tempHtmlCell;

        };



        self.initView(tempSelectedArray);

        this.handleTabChange = function (event, ui) {
            if (ui.option === "checked") {
                if (ui.value === "WE") {
                    $('.WEinfo').each(function (i, v) {
                        $(v).css('display', 'block');
                    });

                    $('.BIinfo').each(function (i, v) {
                        $(v).css('display', 'none');
                    });

                    $('.EHinfo').each(function (i, v) {
                        $(v).css('display', 'none');
                    });
                } else if (ui.value === "BI") {
                    $('.BIinfo').each(function (i, v) {
                        $(v).css('display', 'block');
                    });

                    $('.WEinfo').each(function (i, v) {
                        $(v).css('display', 'none');
                    });

                    $('.EHinfo').each(function (i, v) {
                        $(v).css('display', 'none');
                    });
                } else {
                    $('.EHinfo').each(function (i, v) {
                        $(v).css('display', 'block');
                    });

                    $('.WEinfo').each(function (i, v) {
                        $(v).css('display', 'none');
                    });

                    $('.BIinfo').each(function (i, v) {
                        $(v).css('display', 'none');
                    });
                }
            }
        };



        self.handleActivated = function (info) {
            // Implement if needed

        };

        /**
         * Optional ViewModel method invoked after the View is inserted into the
         * document DOM.  The application can put logic that requires the DOM being
         * attached here.
         * @param {Object} info - An object with the following key-value pairs:
         * @param {Node} info.element - DOM element or where the binding is attached. This may be a 'virtual' element (comment node).
         * @param {Function} info.valueAccessor - The binding's value accessor.
         * @param {boolean} info.fromCache - A boolean indicating whether the module was retrieved from cache.
         */
        self.handleAttached = function (info) {
            $("#compare_container").empty();
//            document.getElementById("compare_container").innerHTML = '';
            ;
//            $("#compare_container")[0].innerHTML = "";
        };

    }

    return compViewContentViewModel;
});
