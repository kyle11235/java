define(['ojs/ojcore', 'jquery'],
        function (oj, $) {
            /**
             * The view model for the main content view template
             */
            function appVariablesModel() {
                var self = this;


//                self.ServiceURL = "http://xxx:8080/hcm/api/employee";
                self.ServiceURL = "http://" + window.location.host + "/hcm/api/employee";

                self.getIcon = function (id) {
                    var imageIcon;
                    imageIcon = "css/images/" + id + ".png";
                    return imageIcon;
                };

                self.listData = {};
                self.savedSearchText = "";
                
                self.compareArray = new Array();
                self.printPeople = new Array();
                self.IDs = new Array();

                self.getFinalData = function (JSONReturn) {

                    if (JSONReturn !== undefined && JSONReturn[0] !== "") {
                        self.totalCloud = new Array();
                        var newArray = JSON.parse(JSONReturn);
                        for (var i = 0; i < newArray.length; i++) {
                            var value = newArray[i];
                            var color = self.getColor(value.type);
                            var newName = self.Trim(value.name);
                            var Icon = self.getIcon(newName);
                            value.color = color;
                            value.icon = Icon;
                            value.id = i;
                            self.totalCloud.push(value);
                        }

                        return self.totalCloud;
                    } else {

                    }

                };



                self.Trim = function (str)
                {
                    var result;
                    result = str.replace(/\s/g, "");
                    return result;
                };


                self.getFinalData();
            }

            return new appVariablesModel();
        });