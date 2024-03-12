package com.laker.admin.module.sys.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laker.admin.framework.aop.metrics.Metrics;
import com.laker.admin.framework.model.Response;
import com.laker.admin.module.sys.entity.SysPower;
import com.laker.admin.module.sys.pojo.MenuVo;
import com.laker.admin.module.sys.pojo.MyMenuVo;
import com.laker.admin.module.sys.service.ISysMenuService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 系统菜单表 前端控制器
 * </p>
 *
 * @author laker
 * @since 2021-08-04
 */
@RestController
@RequestMapping("/sys/menu")
@Metrics
public class SysMenuController {
    @Autowired
    ISysMenuService sysMenuService;

    @GetMapping
    public Response pageAll(@RequestParam(required = false, defaultValue = "1") long current,
                            @RequestParam(required = false, defaultValue = "10") long size) {
        Page roadPage = new Page<>(current, size);
        LambdaQueryWrapper<SysPower> queryWrapper = new QueryWrapper().lambda();
        Page pageList = sysMenuService.page(roadPage, queryWrapper);
        return Response.ok(pageList);
    }


    @GetMapping("/list")
    public Response list() {
        List<SysPower> list = sysMenuService.list(Wrappers.<SysPower>lambdaQuery().orderByAsc(SysPower::getSort));
        return Response.ok(list);
    }

    @PostMapping
    @SaCheckPermission("menu.update")
    public Response saveOrUpdate(@RequestBody SysPower param) {
        return Response.ok(sysMenuService.saveOrUpdate(param));
    }

    @GetMapping("/{id}")
    public Response get(@PathVariable Long id) {
        return Response.ok(sysMenuService.getById(id));
    }


    @GetMapping("/tree")
    public List<MenuVo> tree() {
        return sysMenuService.myMenu();
    }

    @SneakyThrows
    @GetMapping("/mytree")
    public Response mytree() {
        String res = """
                [
                  {
                    path: '/external-link',
                    component: '#',
                    meta: {},
                    name: 'ExternalLink',
                    children: [
                      {
                        path: 'https://element-plus-admin-doc.cn/',
                        name: 'DocumentLink',
                        meta: {
                          title: 'router.document',
                          icon: 'clarity:document-solid'
                        }
                      }
                    ]
                  },
                  {
                    path: '/guide',
                    component: '#',
                    name: 'Guide',
                    meta: {},
                    children: [
                      {
                        path: 'index',
                        component: 'views/Guide/Guide',
                        name: 'GuideDemo',
                        meta: {
                          title: 'router.guide',
                          icon: 'cib:telegram-plane'
                        }
                      }
                    ]
                  },
                  {
                    path: '/components',
                    component: '#',
                    redirect: '/components/form/default-form',
                    name: 'ComponentsDemo',
                    meta: {
                      title: 'router.component',
                      icon: 'bx:bxs-component',
                      alwaysShow: true
                    },
                    children: [
                      {
                        path: 'form',
                        component: '##',
                        name: 'Form',
                        meta: {
                          title: 'router.form',
                          alwaysShow: true
                        },
                        children: [
                          {
                            path: 'default-form',
                            component: 'views/Components/Form/DefaultForm',
                            name: 'DefaultForm',
                            meta: {
                              title: 'router.defaultForm'
                            }
                          },
                          {
                            path: 'use-form',
                            component: 'views/Components/Form/UseFormDemo',
                            name: 'UseForm',
                            meta: {
                              title: 'UseForm'
                            }
                          }
                        ]
                      },
                      {
                        path: 'table',
                        component: '##',
                        redirect: '/components/table/default-table',
                        name: 'TableDemo',
                        meta: {
                          title: 'router.table',
                          alwaysShow: true
                        },
                        children: [
                          {
                            path: 'default-table',
                            component: 'views/Components/Table/DefaultTable',
                            name: 'DefaultTable',
                            meta: {
                              title: 'router.defaultTable'
                            }
                          },
                          {
                            path: 'use-table',
                            component: 'views/Components/Table/UseTableDemo',
                            name: 'UseTable',
                            meta: {
                              title: 'UseTable'
                            }
                          },
                          {
                            path: 'tree-table',
                            component: 'views/Components/Table/TreeTable',
                            name: 'TreeTable',
                            meta: {
                              title: 'TreeTable'
                            }
                          },
                          {
                            path: 'table-image-preview',
                            component: 'views/Components/Table/TableImagePreview',
                            name: 'TableImagePreview',
                            meta: {
                              title: 'router.PicturePreview'
                            }
                          },
                          {
                            path: 'table-video-preview',
                            component: 'views/Components/Table/TableVideoPreview',
                            name: 'TableVideoPreview',
                            meta: {
                              title: 'router.tableVideoPreview'
                            }
                          },
                          {
                            path: 'card-table',
                            component: 'views/Components/Table/CardTable',
                            name: 'CardTable',
                            meta: {
                              title: 'router.cardTable'
                            }
                          }
                          // {
                          //   path: 'ref-table',
                          //   component: 'views/Components/Table/RefTable',
                          //   name: 'RefTable',
                          //   meta: {
                          //     title: 'RefTable'
                          //   }
                          // }
                        ]
                      },
                      {
                        path: 'editor-demo',
                        component: '##',
                        redirect: '/components/editor-demo/editor',
                        name: 'EditorDemo',
                        meta: {
                          title: 'router.editor',
                          alwaysShow: true
                        },
                        children: [
                          {
                            path: 'editor',
                            component: 'views/Components/Editor/Editor',
                            name: 'Editor',
                            meta: {
                              title: 'router.richText'
                            }
                          },
                          {
                            path: 'json-editor',
                            component: 'views/Components/Editor/JsonEditor',
                            name: 'JsonEditor',
                            meta: {
                              title: 'router.jsonEditor'
                            }
                          }
                        ]
                      },
                      {
                        path: 'search',
                        component: 'views/Components/Search',
                        name: 'Search',
                        meta: {
                          title: 'router.search'
                        }
                      },
                      {
                        path: 'descriptions',
                        component: 'views/Components/Descriptions',
                        name: 'Descriptions',
                        meta: {
                          title: 'router.descriptions'
                        }
                      },
                      {
                        path: 'image-viewer',
                        component: 'views/Components/ImageViewer',
                        name: 'ImageViewer',
                        meta: {
                          title: 'router.imageViewer'
                        }
                      },
                      {
                        path: 'dialog',
                        component: 'views/Components/Dialog',
                        name: 'Dialog',
                        meta: {
                          title: 'router.dialog'
                        }
                      },
                      {
                        path: 'icon',
                        component: 'views/Components/Icon',
                        name: 'Icon',
                        meta: {
                          title: 'router.icon'
                        }
                      },
                      {
                        path: 'icon-picker',
                        component: 'views/Components/IconPicker',
                        name: 'IconPicker',
                        meta: {
                          title: 'router.iconPicker'
                        }
                      },
                      {
                        path: 'echart',
                        component: 'views/Components/Echart',
                        name: 'Echart',
                        meta: {
                          title: 'router.echart'
                        }
                      },
                      {
                        path: 'count-to',
                        component: 'views/Components/CountTo',
                        name: 'CountTo',
                        meta: {
                          title: 'router.countTo'
                        }
                      },
                      {
                        path: 'qrcode',
                        component: 'views/Components/Qrcode',
                        name: 'Qrcode',
                        meta: {
                          title: 'router.qrcode'
                        }
                      },
                      {
                        path: 'highlight',
                        component: 'views/Components/Highlight',
                        name: 'Highlight',
                        meta: {
                          title: 'router.highlight'
                        }
                      },
                      {
                        path: 'infotip',
                        component: 'views/Components/Infotip',
                        name: 'Infotip',
                        meta: {
                          title: 'router.infotip'
                        }
                      },
                      {
                        path: 'input-password',
                        component: 'views/Components/InputPassword',
                        name: 'InputPassword',
                        meta: {
                          title: 'router.inputPassword'
                        }
                      },
                      {
                        path: 'waterfall',
                        component: 'views/Components/Waterfall',
                        name: 'Waterfall',
                        meta: {
                          title: 'router.waterfall'
                        }
                      },
                      {
                        path: 'image-cropping',
                        component: 'views/Components/ImageCropping',
                        name: 'ImageCropping',
                        meta: {
                          title: 'router.imageCropping'
                        }
                      },
                      {
                        path: 'video-player',
                        component: 'views/Components/VideoPlayer',
                        name: 'VideoPlayer',
                        meta: {
                          title: 'router.videoPlayer'
                        }
                      },
                      {
                        path: 'avatars',
                        component: 'views/Components/Avatars',
                        name: 'Avatars',
                        meta: {
                          title: 'router.avatars'
                        }
                      },
                      {
                        path: 'i-agree',
                        component: 'views/Components/IAgree',
                        name: 'IAgree',
                        meta: {
                          title: 'router.iAgree'
                        }
                      }
                    ]
                  },
                  {
                    path: '/function',
                    component: '#',
                    redirect: '/function/multipleTabs',
                    name: 'Function',
                    meta: {
                      title: 'router.function',
                      icon: 'ri:function-fill',
                      alwaysShow: true
                    },
                    children: [
                      {
                        path: 'multipleTabs',
                        component: 'views/Function/MultipleTabs',
                        name: 'MultipleTabs',
                        meta: {
                          title: 'router.multipleTabs'
                        }
                      },
                      {
                        path: 'multiple-tabs-demo/:id',
                        component: 'views/Function/MultipleTabsDemo',
                        name: 'MultipleTabsDemo',
                        meta: {
                          hidden: true,
                          title: 'router.details',
                          canTo: true
                        }
                      },
                      {
                        path: 'request',
                        component: 'views/Function/Request',
                        name: 'Request',
                        meta: {
                          title: 'router.request'
                        }
                      },
                      {
                        path: 'test',
                        component: 'views/Function/Test',
                        name: 'Test',
                        meta: {
                          title: 'router.permission',
                          permission: ['add', 'edit', 'delete']
                        }
                      }
                    ]
                  },
                  {
                    path: '/hooks',
                    component: '#',
                    redirect: '/hooks/useWatermark',
                    name: 'Hooks',
                    meta: {
                      title: 'hooks',
                      icon: 'ic:outline-webhook',
                      alwaysShow: true
                    },
                    children: [
                      {
                        path: 'useWatermark',
                        component: 'views/hooks/useWatermark',
                        name: 'UseWatermark',
                        meta: {
                          title: 'useWatermark'
                        }
                      },
                      {
                        path: 'useTagsView',
                        component: 'views/hooks/useTagsView',
                        name: 'UseTagsView',
                        meta: {
                          title: 'useTagsView'
                        }
                      },
                      {
                        path: 'useValidator',
                        component: 'views/hooks/useValidator',
                        name: 'UseValidator',
                        meta: {
                          title: 'useValidator'
                        }
                      },
                      {
                        path: 'useCrudSchemas',
                        component: 'views/hooks/useCrudSchemas',
                        name: 'UseCrudSchemas',
                        meta: {
                          title: 'useCrudSchemas'
                        }
                      },
                      {
                        path: 'useClipboard',
                        component: 'views/hooks/useClipboard',
                        name: 'UseClipboard',
                        meta: {
                          title: 'useClipboard'
                        }
                      },
                      {
                        path: 'useNetwork',
                        component: 'views/hooks/useNetwork',
                        name: 'UseNetwork',
                        meta: {
                          title: 'useNetwork'
                        }
                      }
                    ]
                  },
                  {
                    path: '/level',
                    component: '#',
                    redirect: '/level/menu1/menu1-1/menu1-1-1',
                    name: 'Level',
                    meta: {
                      title: 'router.level',
                      icon: 'carbon:skill-level-advanced'
                    },
                    children: [
                      {
                        path: 'menu1',
                        name: 'Menu1',
                        component: '##',
                        redirect: '/level/menu1/menu1-1/menu1-1-1',
                        meta: {
                          title: 'router.menu1'
                        },
                        children: [
                          {
                            path: 'menu1-1',
                            name: 'Menu11',
                            component: '##',
                            redirect: '/level/menu1/menu1-1/menu1-1-1',
                            meta: {
                              title: 'router.menu11',
                              alwaysShow: true
                            },
                            children: [
                              {
                                path: 'menu1-1-1',
                                name: 'Menu111',
                                component: 'views/Level/Menu111',
                                meta: {
                                  title: 'router.menu111'
                                }
                              }
                            ]
                          },
                          {
                            path: 'menu1-2',
                            name: 'Menu12',
                            component: 'views/Level/Menu12',
                            meta: {
                              title: 'router.menu12'
                            }
                          }
                        ]
                      },
                      {
                        path: 'menu2',
                        name: 'Menu2Demo',
                        component: 'views/Level/Menu2',
                        meta: {
                          title: 'router.menu2'
                        }
                      }
                    ]
                  },
                  {
                    path: '/example',
                    component: '#',
                    redirect: '/example/example-dialog',
                    name: 'Example',
                    meta: {
                      title: 'router.example',
                      icon: 'ep:management',
                      alwaysShow: true
                    },
                    children: [
                      {
                        path: 'example-dialog',
                        component: 'views/Example/Dialog/ExampleDialog',
                        name: 'ExampleDialog',
                        meta: {
                          title: 'router.exampleDialog'
                        }
                      },
                      {
                        path: 'example-page',
                        component: 'views/Example/Page/ExamplePage',
                        name: 'ExamplePage',
                        meta: {
                          title: 'router.examplePage'
                        }
                      },
                      {
                        path: 'example-add',
                        component: 'views/Example/Page/ExampleAdd',
                        name: 'ExampleAdd',
                        meta: {
                          title: 'router.exampleAdd',
                          noTagsView: true,
                          noCache: true,
                          hidden: true,
                          showMainRoute: true,
                          activeMenu: '/example/example-page'
                        }
                      },
                      {
                        path: 'example-edit',
                        component: 'views/Example/Page/ExampleEdit',
                        name: 'ExampleEdit',
                        meta: {
                          title: 'router.exampleEdit',
                          noTagsView: true,
                          noCache: true,
                          hidden: true,
                          showMainRoute: true,
                          activeMenu: '/example/example-page'
                        }
                      },
                      {
                        path: 'example-detail',
                        component: 'views/Example/Page/ExampleDetail',
                        name: 'ExampleDetail',
                        meta: {
                          title: 'router.exampleDetail',
                          noTagsView: true,
                          noCache: true,
                          hidden: true,
                          showMainRoute: true,
                          activeMenu: '/example/example-page'
                        }
                      }
                    ]
                  },
                  {
                    path: '/error',
                    component: '#',
                    redirect: '/error/404',
                    name: 'Error',
                    meta: {
                      title: 'router.errorPage',
                      icon: 'ci:error',
                      alwaysShow: true
                    },
                    children: [
                      {
                        path: '404-demo',
                        component: 'views/Error/404',
                        name: '404Demo',
                        meta: {
                          title: '404'
                        }
                      },
                      {
                        path: '403-demo',
                        component: 'views/Error/403',
                        name: '403Demo',
                        meta: {
                          title: '403'
                        }
                      },
                      {
                        path: '500-demo',
                        component: 'views/Error/500',
                        name: '500Demo',
                        meta: {
                          title: '500'
                        }
                      }
                    ]
                  },
                  {
                    path: '/authorization',
                    component: '#',
                    redirect: '/authorization/user',
                    name: 'Authorization',
                    meta: {
                      title: 'router.authorization',
                      icon: 'eos-icons:role-binding',
                      alwaysShow: true
                    },
                    children: [
                      {
                        path: 'department',
                        component: 'views/Authorization/Department/Department',
                        name: 'Department',
                        meta: {
                          title: 'router.department'
                        }
                      },
                      {
                        path: 'user',
                        component: 'views/Authorization/User/User',
                        name: 'User',
                        meta: {
                          title: 'router.user'
                        }
                      },
                      {
                        path: 'menu',
                        component: 'views/Authorization/Menu/Menu',
                        name: 'Menu',
                        meta: {
                          title: 'router.menuManagement'
                        }
                      },
                      {
                        path: 'role',
                        component: 'views/Authorization/Role/Role',
                        name: 'Role',
                        meta: {
                          title: 'router.role'
                        }
                      }
                    ]
                  }
                ]
                """;
        List<MyMenuVo> menuVos = new ArrayList<>();
        ArrayList<MyMenuVo> arrayList = new ArrayList<>();
        MyMenuVo dashboard = MyMenuVo.builder()
                .path("/dashboard").name("Dashboard").component("#")
                .meta(MyMenuVo.Meta.builder()
                        .title("router.dashboard")
                        .icon("ant-design:dashboard-filled")
                        .alwaysShow(true).build())
                .children(arrayList).build();
        MyMenuVo analysis = MyMenuVo.builder()
                .path("analysis").name("'Analysis'").component("views/Dashboard/Analysis")
                .meta(MyMenuVo.Meta.builder()
                        .title("router.analysis")
                        .noCache(true)
                        .affix(true).build())
                .children(new ArrayList<>()).build();
        arrayList.add(analysis);
        MyMenuVo workplace = MyMenuVo.builder()
                .path("workplace").name("Workplace").component("views/Dashboard/Workplace")
                .meta(MyMenuVo.Meta.builder()
                        .title("router.workplace")
                        .noCache(true)
                        .affix(true).build())
                .children(new ArrayList<>()).build();
        arrayList.add(workplace);

        menuVos.add(dashboard);
        return Response.ok(menuVos);
    }


    @GetMapping("/selectTree")
    public Response selectTree() {
        List<MenuVo> menuVos = sysMenuService.myMenu();
        return Response.ok(menuVos);
    }

    @DeleteMapping("/{id}")
    @SaCheckPermission("menu.delete")
    public Response delete(@PathVariable Long id) {
        return Response.ok(sysMenuService.removeById(id));
    }
}