layui.define(['jquery', 'element', 'table', 'yaml', 'common'], function (exports) {
    "use strict";

    var MOD_NAME = 'easyAdmin';
    var $ = layui.jquery;
    var table = layui.table;
    var element = layui.element;
    var common = layui.common;

    var easyAdmin = new function () {

        this.GetAdminServerUrl = function () {
            return layui.data('easyAdmin').serverUrl;
        }

        this.GetTokenQueryString = function () {
            var user = layui.data('user');
            // 用于判断未登录跳转到登录页
            if (JSON.stringify(user) == "{}") {
                console.log("当前浏览器存储中没有用户信息，讲跳转到login.html")
                location.href = "login.html";
            }
            return user.token.name + "=" + user.token.value;
        }
        /**
         * 通用 http请求 参数同 ajax 有部分扩展
         * sendToken：是否发送token默认发送
         * @param options
         */
        this.http = function (options) {
            var defaults = {
                type: 'get',
                headers: {},
                data: {},
                dataType: null,
                async: true,
                cache: false,
                // 是否发送token默认发送
                sendToken: true,
                beforeSend: null,
                success: null,
                complete: null
            };
            var o = $.extend({}, defaults, options);
            // 配置服务端地址
            var adminServerUrl = layui.data('easyAdmin').serverUrl;

            $.ajax({
                url: adminServerUrl + o.url,
                type: o.type,
                headers: {
                    'Content-Type': o.contentType,
                    // 'access_token': o.token
                },
                data: o.data,
                dataType: o.dataType,
                async: o.async,
                beforeSend: function (request) {
                    if (o.sendToken) {
                        o.beforeSend && o.beforeSend();
                        var user = layui.data('user');
                        // 用于判断未登录跳转到登录页
                        if (JSON.stringify(user) == "{}") {
                            console.log("当前浏览器存储中没有用户信息，讲跳转到login.html")
                            location.href = "login.html";
                        }

                        request.setRequestHeader(user.token.name, user.token.value);
                    }
                },
                success: function (res) {
                    o.success && o.success(res);
                },
                complete: function () {
                    o.complete && o.complete();
                },
                error: function () {
                    o.error && o.error();
                }
            });
        };

        /**
         *
         * @param uri 地址
         * @param success 成功回调
         * @param sendToken 是否发送token
         * @param async 是否异步
         */
        this.httpGet = function (uri, success, sendToken, async) {
            this.http({
                url: uri,
                success: success,
                sendToken: sendToken,
                async: async
            })
        };

        /**
         * 表格渲染
         * @param options
         */
        this.tableRender = function (options) {
            var defaults = {
                sendToken: true
            };
            var o = $.extend({}, defaults, options);
            // 配置服务端地址
            var adminServerUrl = layui.data('easyAdmin').serverUrl;
            var user;
            if (o.sendToken) {
                user = layui.data('user');
                // 用于判断未登录跳转到登录页
                if (JSON.stringify(user) == "{}") {
                    console.log("当前浏览器存储中没有用户信息，讲跳转到login.html")
                    location.href = "login.html";
                }
            }
            var tokenName = user.token.name;
            var tokenValue = user.token.value;
            var headers = {};
            headers[tokenName] = tokenValue;
            table.render({
                elem: o.elem, // 对应table的id
                headers: headers,
                url: adminServerUrl + o.url,
                page: o.page, // 分页参数可以自定义
                cols: o.cols, // 列表示
                skin: o.skin, // 表格样式
                toolbar: o.toolbar, // 表格头部工具栏 可定义左上角和右上角工具栏,一般用于左上角配置
                // 自由配置头部工具栏右侧的图标按钮
                defaultToolbar: o.defaultToolbar
            });
        }

        /**
         * tableId : 'user-table'
         */

        this.tableRefresh = function (tableId) {
            table.reload(tableId);
        }


        /**
         * 跳转到add页面
         */
        this.JumpAdd = function (path) {
            layer.open({
                type: 2,
                title: '新增',
                shade: 0.1,
                area: [common.isModile() ? '100%' : '500px', common.isModile() ? '100%' : '500px'],
                content: path + 'add.html'
            });
        }
        /**
         * 跳转到edit页面
         */
        this.JumpEdit = function (MODULE_PATH, id) {
            layer.open({
                type: 2,
                title: '修改',
                shade: 0.1,
                area: ['500px', '500px'],
                content: [MODULE_PATH + 'edit.html?id=' + id, 'no']
            });
        }
        /**
         * 获取url中的 参数
         * @param name 参数名称
         * @returns {string}
         */
        this.getQueryString = function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r !== null)
                return unescape(r[2]);
            return "";
        }

        /**
         * 获取当前表格选中字段
         * @param obj 表格回调参数
         * @param field 要获取的字段
         * */
        this.checkField = function (obj, field) {
            let data = table.checkStatus(obj.config.id).data;
            if (data.length === 0) {
                return "";
            }
            let ids = "";
            for (let i = 0; i < data.length; i++) {
                ids += data[i][field] + ",";
            }
            ids = ids.substr(0, ids.length - 1);
            return ids;
        }

        /**
         * 当前是否为与移动端
         * */
        this.isModile = function () {
            if ($(window).width() <= 768) {
                return true;
            }
            return false;
        }

        /**
         * <p>
         *     新增 button lay-filter="save"
         *    <button type="submit" lay-submit lay-filter="save"
         *     其父 main页面   id="table"
         *     <table id="table"
         * </p>
         *
         */
        this.FormAddSave = function (url) {
            form.on('submit(save)', function (data) {
                easyAdmin.http({
                    url: url,
                    data: JSON.stringify(data.field),
                    dataType: 'json',
                    contentType: 'application/json',
                    type: 'post',
                    success: function (result) {
                        if (result.success) {
                            layer.msg(result.msg,
                                {
                                    icon: 1,
                                    time: 1000,
                                    area: ['100px', '80px'],
                                    content: "新增成功"
                                }
                                , function () {
                                    parent.layer.close(parent.layer.getFrameIndex(window.name));//关闭当前页
                                    parent.layui.table.reload("table");
                                });
                        } else {
                            layer.msg(result.msg,
                                {icon: 2, time: 1000, area: ['100px', '80px']}
                            );
                        }
                    }
                })
                return false;
            });
        }


        /**
         * 提交 json 数据
         * @param data 提交数据
         * @param href 提交接口
         * @param table 刷新父级表
         *
         * */
        this.submit = function (data, href, table, callback) {
            $.ajax({
                url: href,
                data: JSON.stringify(data),
                dataType: 'json',
                contentType: 'application/json',
                type: 'post',
                success: callback != null ? callback(result) : function (result) {
                    if (result.success) {
                        layer.msg(result.msg, {icon: 1, time: 1000}, function () {
                            parent.layer.close(parent.layer.getFrameIndex(window.name));//关闭当前页
                            parent.layui.table.reload(table);
                        });
                    } else {
                        layer.msg(result.msg, {icon: 2, time: 1000});
                    }
                }
            })
        }
    }
    exports(MOD_NAME, easyAdmin);
});