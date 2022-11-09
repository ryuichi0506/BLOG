import Vue from 'vue'
import VueRouter from 'vue-router'

import Index from '../views/Index.vue'
import Login from '../views/user/Login.vue'
import Regist from '../views/user/Regist.vue'
 
import AdminLogin from '../views/admin/AdminLogin.vue'

import Backend from '../views/admin/Backend.vue'
import StudyManage from '../views/admin/StudyManage.vue'
import AnimeManage from '../views/admin/AnimeManage.vue'
import LifeManage from '../views/admin/LifeManage.vue'
import MusicManage from '../views/admin/MusicManage.vue'
import UserManage from '../views/admin/UserManage.vue'
import TagManage from '../views/admin/TagManage.vue'
import MessageManage from '../views/admin/MessageManage.vue'

import Study_Detail from '../views/Study-Detail.vue'
import Anime_Detail from '../views/Anime-Detail.vue'
import Life_Detail from '../views/Life-Detail.vue'
import Music_Detail from '../views/Music-Detail.vue'

import Home from '../views/Home.vue'
import Message from '../views/MessageBoard.vue'
import Space from '../views/Space.vue'
import Search from '../views/Search.vue'

import Upload from '../views/Upload.vue'
import UploadStudy from '../views/Upload-Study.vue'
import UploadAnime from '../views/Upload-Anime.vue'
import UploadLife from '../views/Upload-Life.vue'
import UploadMusic from '../views/Upload-Music.vue'

import Infocenter from '../views/infocenter/Infocenter.vue'
import Info from '../views/infocenter/Info.vue'
import Avatar from '../views/infocenter/Avatar.vue'
import Blogs_study from '../views/infocenter/Blogs-Study.vue'
import Blogs_anime from '../views/infocenter/Blogs-Anime.vue'
import Blogs_life from '../views/infocenter/Blogs-Life.vue'
import Blogs_music from '../views/infocenter/Blogs-Music.vue'

import Draft_study from '../views/infocenter/Draft-Study.vue'
import Draft_anime from '../views/infocenter/Draft-Anime.vue'
import Draft_life from '../views/infocenter/Draft-Life.vue'
import Draft_music from '../views/infocenter/Draft-Music.vue'

import Study from '../views/type/Study.vue'
import Anime from '../views/type/Anime.vue'
import Life from '../views/type/Life.vue'
import Music from '../views/type/Music.vue'

Vue.use(VueRouter)

const routes = [
    { path: '/', redirect: '/index' },
    {
        path: '/index',
        component: Index,
        redirect: '/login',
        meta: {
            title: '基于SSM的多人博客系统'
        },
        children: [
            {
                path: '/login',
                component: Login,
                meta: {
                    title: '基于SSM的多人博客系统'
                }
            },
            {
                path: '/regist',
                component: Regist,
                meta: {
                    title: '基于SSM的多人博客系统'
                }
            },
            {
                path: '/admin',
                component: AdminLogin,
                meta: {
                    title: '多人博客系统后台登录'
                }
            }
        ]
    },

    // 后台管理页面
    {
        path: '/backend',
        component: Backend,
        redirect: '/backend/blog/study',
        meta: {
            title: '多人博客系统后台管理'
        },
        children: [
            {
                path: '/backend/blog/study',
                component: StudyManage,
                meta: {
                    title: '多人博客系统后台管理'
                },
            },
            {
                path: '/backend/blog/anime',
                component: AnimeManage,
                meta: {
                    title: '多人博客系统后台管理'
                },
            },
            {
                path: '/backend/blog/life',
                component: LifeManage,
                meta: {
                    title: '多人博客系统后台管理'
                },
            },
            {
                path: '/backend/blog/music',
                component: MusicManage,
                meta: {
                    title: '多人博客系统后台管理'
                },
            },
            {
                path: '/backend/user',
                component: UserManage,
                meta: {
                    title: '多人博客系统后台管理'
                },
            },
            {
                path: '/backend/tag',
                component: TagManage,
                meta: {
                    title: '多人博客系统后台管理'
                },
            },
            {
                path: '/backend/message',
                component: MessageManage,
                meta: {
                    title: '多人博客系统后台管理'
                },
            },
        ]
    },

    // 前台页面
    {
        path: '/home',
        component: Home,
        meta: {
            title: '首页'
        }
    },
    {
        path: '/messageboard',
        component: Message,
        meta: {
            title: '留言板'
        }
    },
    {
        path: '/space/:userId',
        component: Space,
    },
    {
        path: '/search',
        component: Search,
        name: '/search',
        meta: {
            title: '搜索'
        }
    },
    {
        path: '/upload',
        component: Upload,
        redirect: '/upload/study',
        children: [
            {
                path: '/upload/study',
                name: '/upload/study',
                component: UploadStudy,
                meta: {
                    title: '投稿-学习'
                }
            },
            {
                path: '/upload/anime',
                name: '/upload/anime',
                component: UploadAnime,
                meta: {
                    title: '投稿-动漫'
                }
            },
            {
                path: '/upload/life',
                name: '/upload/life',
                component: UploadLife,
                meta: {
                    title: '投稿-生活'
                }
            },
            {
                path: '/upload/music',
                name: '/upload/music',
                component: UploadMusic,
                meta: {
                    title: '投稿-音乐'
                }
            }
        ]
    },
    {
        path: '/study',
        component: Study,
        meta: {
            title: '学习'
        }
    },
    {
        path: '/anime',
        component: Anime,
        meta: {
            title: '动漫'
        }
    },
    {
        path: '/life',
        component: Life,
        meta: {
            title: '生活'
        }
    },
    {
        path: '/music',
        component: Music,
        meta: {
            title: '音乐'
        }
    },
    {
        path: '/study/:studyId',
        component: Study_Detail
    },
    {
        path: '/anime/:animeId',
        component: Anime_Detail
    },
    {
        path: '/life/:lifeId',
        component: Life_Detail
    },
    {
        path: '/music/:musicId',
        component: Music_Detail
    },
    {
        path: '/infocenter',
        component: Infocenter,
        redirect: '/info',
        children: [
            {
                path: '/info',
                component: Info,
                meta: {
                    title: '个人资料'
                }
            },
            {
                path: '/avatar',
                component: Avatar,
                meta: {
                    title: '我的头像'
                }
            },
            {
                path: '/blogs/study',
                component: Blogs_study,
                meta: {
                    title: '我的博客 - 学习'
                }
            },
            {
                path: '/blogs/anime',
                component: Blogs_anime,
                meta: {
                    title: '我的博客 - 动漫'
                }
            },
            {
                path: '/blogs/life',
                component: Blogs_life,
                meta: {
                    title: '我的博客 - 生活'
                }
            },
            {
                path: '/blogs/music',
                component: Blogs_music,
                meta: {
                    title: '我的博客 - 音乐'
                }
            },
            {
                path: '/draft/study',
                component: Draft_study,
                meta: {
                    title: '草稿箱 - 学习'
                }
            },
            {
                path: '/draft/anime',
                component: Draft_anime,
                meta: {
                    title: '草稿箱 - 动漫'
                }
            },
            {
                path: '/draft/life',
                component: Draft_life,
                meta: {
                    title: '草稿箱 - 生活'
                }
            },
            {
                path: '/draft/music',
                component: Draft_music,
                meta: {
                    title: '草稿箱 - 音乐'
                }
            }
        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    routes
})

// 挂载路由导航守卫
router.beforeEach((to, from, next) => {
    // to 表示将要访问的路径
    // from 表示从哪个路径跳转来
    // next 是一个函数，表示放行
    // next() 放行  
    // next('/login') 表示强制跳转到/login
    if(to.path === '/admin') return next();
    if(to.path === '/login') return next();
    if(to.path === '/regist') return next();

    const admin = JSON.parse(window.sessionStorage.getItem('admin'));
    const user = JSON.parse(window.sessionStorage.getItem('user'));
    const visitor = JSON.parse(window.sessionStorage.getItem('visitor'));

    // 若既没有用户也没有游客也没有管理员信息，则返回登录界面
    if(!user && !visitor && !admin){
        return next('/login')
    } 

    // 非用户不能访问的路由列表
    const pathArray = [
        '/upload/study','/upload/anime','/upload/life','/upload/music',
        '/blogs/study','/blogs/anime','/blogs/life','/blogs/music',
        '/draft/study','/draft/anime','/draft/life','/draft/music',
        '/info','/avatar'
    ]

    if(!user){
        if(to.path === '/space' || pathArray.indexOf(to.path) > -1){
            Vue.prototype.$confirm('登录后才能进行访问,是否登录?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                next('/login')
            }).catch(() => {
                return        
            });
            router.replace(from);   // 将 '来的路径' 替换掉 '将要跳转的被禁止的路径'，以防止 '被禁止的路径' 存于历史纪录中
            return next(false)      // 禁止路由跳转
        }else{
            next();
        }
    }else{
        next()
    }
    next();
});


export default router
