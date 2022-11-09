// import Vue from 'vue'
// import Element from 'element-ui'

// Vue.use(Element)
import Vue from 'vue'
import { 
    Button, 
    Form,
    FormItem, 
    Input, 
    MessageBox,
    Message, 
    Container, 
    Header, 
    Aside, 
    Main, 
    Menu, 
    MenuItem,
    MenuItemGroup,
    Submenu,
    Breadcrumb,
    BreadcrumbItem,
    Card,
    Row,
    Col,
    Table,
    TableColumn,
    Switch,
    Tooltip,
    Pagination,
    Dialog,
    Image,
    Tag,
    Carousel,
    CarouselItem,
    Divider,
    Backtop,
    Dropdown,
    DropdownItem,
    DropdownMenu,
    Popover,
    TabPane,
    Tabs,
    Timeline,
    TimelineItem,
    Radio,
    Upload,
    Notification,
    Loading,
} 
from 'element-ui'

Vue.use(Button)
Vue.use(Form)
Vue.use(FormItem)
Vue.use(Input)
Vue.use(Container)
Vue.use(Header)
Vue.use(Aside)
Vue.use(Main)
Vue.use(Menu)
Vue.use(MenuItem)
Vue.use(MenuItemGroup)
Vue.use(Submenu)
Vue.use(Breadcrumb)
Vue.use(BreadcrumbItem)
Vue.use(Card)
Vue.use(Row)
Vue.use(Col)
Vue.use(Table)
Vue.use(TableColumn)
Vue.use(Switch)
Vue.use(Tooltip)
Vue.use(Pagination)
Vue.use(Dialog)
Vue.use(Image)
Vue.use(Tag)
Vue.use(Carousel)
Vue.use(CarouselItem)
Vue.use(Divider)
Vue.use(Backtop)
Vue.use(Dropdown)
Vue.use(DropdownItem)
Vue.use(DropdownMenu)
Vue.use(Popover)
Vue.use(Tabs)
Vue.use(TabPane)
Vue.use(Timeline)
Vue.use(TimelineItem)
Vue.use(Radio)
Vue.use(Upload)
Vue.use(Loading)

Vue.prototype.$msgbox = MessageBox;
Vue.prototype.$message = Message
Vue.prototype.$confirm = MessageBox.confirm
Vue.prototype.$notify = Notification

import 'element-ui/lib/theme-chalk/index.css'