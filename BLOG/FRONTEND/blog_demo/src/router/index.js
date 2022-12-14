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
            title: '??????SSM?????????????????????'
        },
        children: [
            {
                path: '/login',
                component: Login,
                meta: {
                    title: '??????SSM?????????????????????'
                }
            },
            {
                path: '/regist',
                component: Regist,
                meta: {
                    title: '??????SSM?????????????????????'
                }
            },
            {
                path: '/admin',
                component: AdminLogin,
                meta: {
                    title: '??????????????????????????????'
                }
            }
        ]
    },

    // ??????????????????
    {
        path: '/backend',
        component: Backend,
        redirect: '/backend/blog/study',
        meta: {
            title: '??????????????????????????????'
        },
        children: [
            {
                path: '/backend/blog/study',
                component: StudyManage,
                meta: {
                    title: '??????????????????????????????'
                },
            },
            {
                path: '/backend/blog/anime',
                component: AnimeManage,
                meta: {
                    title: '??????????????????????????????'
                },
            },
            {
                path: '/backend/blog/life',
                component: LifeManage,
                meta: {
                    title: '??????????????????????????????'
                },
            },
            {
                path: '/backend/blog/music',
                component: MusicManage,
                meta: {
                    title: '??????????????????????????????'
                },
            },
            {
                path: '/backend/user',
                component: UserManage,
                meta: {
                    title: '??????????????????????????????'
                },
            },
            {
                path: '/backend/tag',
                component: TagManage,
                meta: {
                    title: '??????????????????????????????'
                },
            },
            {
                path: '/backend/message',
                component: MessageManage,
                meta: {
                    title: '??????????????????????????????'
                },
            },
        ]
    },

    // ????????????
    {
        path: '/home',
        component: Home,
        meta: {
            title: '??????'
        }
    },
    {
        path: '/messageboard',
        component: Message,
        meta: {
            title: '?????????'
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
            title: '??????'
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
                    title: '??????-??????'
                }
            },
            {
                path: '/upload/anime',
                name: '/upload/anime',
                component: UploadAnime,
                meta: {
                    title: '??????-??????'
                }
            },
            {
                path: '/upload/life',
                name: '/upload/life',
                component: UploadLife,
                meta: {
                    title: '??????-??????'
                }
            },
            {
                path: '/upload/music',
                name: '/upload/music',
                component: UploadMusic,
                meta: {
                    title: '??????-??????'
                }
            }
        ]
    },
    {
        path: '/study',
        component: Study,
        meta: {
            title: '??????'
        }
    },
    {
        path: '/anime',
        component: Anime,
        meta: {
            title: '??????'
        }
    },
    {
        path: '/life',
        component: Life,
        meta: {
            title: '??????'
        }
    },
    {
        path: '/music',
        component: Music,
        meta: {
            title: '??????'
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
                    title: '????????????'
                }
            },
            {
                path: '/avatar',
                component: Avatar,
                meta: {
                    title: '????????????'
                }
            },
            {
                path: '/blogs/study',
                component: Blogs_study,
                meta: {
                    title: '???????????? - ??????'
                }
            },
            {
                path: '/blogs/anime',
                component: Blogs_anime,
                meta: {
                    title: '???????????? - ??????'
                }
            },
            {
                path: '/blogs/life',
                component: Blogs_life,
                meta: {
                    title: '???????????? - ??????'
                }
            },
            {
                path: '/blogs/music',
                component: Blogs_music,
                meta: {
                    title: '???????????? - ??????'
                }
            },
            {
                path: '/draft/study',
                component: Draft_study,
                meta: {
                    title: '????????? - ??????'
                }
            },
            {
                path: '/draft/anime',
                component: Draft_anime,
                meta: {
                    title: '????????? - ??????'
                }
            },
            {
                path: '/draft/life',
                component: Draft_life,
                meta: {
                    title: '????????? - ??????'
                }
            },
            {
                path: '/draft/music',
                component: Draft_music,
                meta: {
                    title: '????????? - ??????'
                }
            }
        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    routes
})

// ????????????????????????
router.beforeEach((to, from, next) => {
    // to ???????????????????????????
    // from ??????????????????????????????
    // next ??????????????????????????????
    // next() ??????  
    // next('/login') ?????????????????????/login
    if(to.path === '/admin') return next();
    if(to.path === '/login') return next();
    if(to.path === '/regist') return next();

    const admin = JSON.parse(window.sessionStorage.getItem('admin'));
    const user = JSON.parse(window.sessionStorage.getItem('user'));
    const visitor = JSON.parse(window.sessionStorage.getItem('visitor'));

    // ?????????????????????????????????????????????????????????????????????????????????
    if(!user && !visitor && !admin){
        return next('/login')
    } 

    // ????????????????????????????????????
    const pathArray = [
        '/upload/study','/upload/anime','/upload/life','/upload/music',
        '/blogs/study','/blogs/anime','/blogs/life','/blogs/music',
        '/draft/study','/draft/anime','/draft/life','/draft/music',
        '/info','/avatar'
    ]

    if(!user){
        if(to.path === '/space' || pathArray.indexOf(to.path) > -1){
            Vue.prototype.$confirm('???????????????????????????,?????????????', '??????', {
                confirmButtonText: '??????',
                cancelButtonText: '??????',
                type: 'warning'
            }).then(() => {
                next('/login')
            }).catch(() => {
                return        
            });
            router.replace(from);   // ??? '????????????' ????????? '?????????????????????????????????'???????????? '??????????????????' ?????????????????????
            return next(false)      // ??????????????????
        }else{
            next();
        }
    }else{
        next()
    }
    next();
});


export default router
