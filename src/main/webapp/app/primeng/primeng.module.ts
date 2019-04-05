import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SoptorshiButtonDemoModule } from './buttons/button/buttondemo.module';
import { SoptorshiSplitbuttonDemoModule } from './buttons/splitbutton/splitbuttondemo.module';

import { SoptorshiDialogDemoModule } from './overlay/dialog/dialogdemo.module';
import { SoptorshiConfirmDialogDemoModule } from './overlay/confirmdialog/confirmdialogdemo.module';
import { SoptorshiLightboxDemoModule } from './overlay/lightbox/lightboxdemo.module';
import { SoptorshiTooltipDemoModule } from './overlay/tooltip/tooltipdemo.module';
import { SoptorshiOverlayPanelDemoModule } from './overlay/overlaypanel/overlaypaneldemo.module';
import { SoptorshiSideBarDemoModule } from './overlay/sidebar/sidebardemo.module';

import { SoptorshiKeyFilterDemoModule } from './inputs/keyfilter/keyfilterdemo.module';
import { SoptorshiInputTextDemoModule } from './inputs/inputtext/inputtextdemo.module';
import { SoptorshiInputTextAreaDemoModule } from './inputs/inputtextarea/inputtextareademo.module';
import { SoptorshiInputGroupDemoModule } from './inputs/inputgroup/inputgroupdemo.module';
import { SoptorshiCalendarDemoModule } from './inputs/calendar/calendardemo.module';
import { SoptorshiCheckboxDemoModule } from './inputs/checkbox/checkboxdemo.module';
import { SoptorshiChipsDemoModule } from './inputs/chips/chipsdemo.module';
import { SoptorshiColorPickerDemoModule } from './inputs/colorpicker/colorpickerdemo.module';
import { SoptorshiInputMaskDemoModule } from './inputs/inputmask/inputmaskdemo.module';
import { SoptorshiInputSwitchDemoModule } from './inputs/inputswitch/inputswitchdemo.module';
import { SoptorshiPasswordIndicatorDemoModule } from './inputs/passwordindicator/passwordindicatordemo.module';
import { SoptorshiAutoCompleteDemoModule } from './inputs/autocomplete/autocompletedemo.module';
import { SoptorshiSliderDemoModule } from './inputs/slider/sliderdemo.module';
import { SoptorshiSpinnerDemoModule } from './inputs/spinner/spinnerdemo.module';
import { SoptorshiRatingDemoModule } from './inputs/rating/ratingdemo.module';
import { SoptorshiSelectDemoModule } from './inputs/select/selectdemo.module';
import { SoptorshiSelectButtonDemoModule } from './inputs/selectbutton/selectbuttondemo.module';
import { SoptorshiListboxDemoModule } from './inputs/listbox/listboxdemo.module';
import { SoptorshiRadioButtonDemoModule } from './inputs/radiobutton/radiobuttondemo.module';
import { SoptorshiToggleButtonDemoModule } from './inputs/togglebutton/togglebuttondemo.module';
import { SoptorshiEditorDemoModule } from './inputs/editor/editordemo.module';

import { SoptorshiMessagesDemoModule } from './messages/messages/messagesdemo.module';
import { SoptorshiToastDemoModule } from './messages/toast/toastdemo.module';
import { SoptorshiGalleriaDemoModule } from './multimedia/galleria/galleriademo.module';

import { SoptorshiFileUploadDemoModule } from './fileupload/fileupload/fileuploaddemo.module';

import { SoptorshiAccordionDemoModule } from './panel/accordion/accordiondemo.module';
import { SoptorshiPanelDemoModule } from './panel/panel/paneldemo.module';
import { SoptorshiTabViewDemoModule } from './panel/tabview/tabviewdemo.module';
import { SoptorshiFieldsetDemoModule } from './panel/fieldset/fieldsetdemo.module';
import { SoptorshiToolbarDemoModule } from './panel/toolbar/toolbardemo.module';
import { SoptorshiScrollPanelDemoModule } from './panel/scrollpanel/scrollpaneldemo.module';
import { SoptorshiCardDemoModule } from './panel/card/carddemo.module';
import { SoptorshiFlexGridDemoModule } from './panel/flexgrid/flexgriddemo.module';

import { SoptorshiTableDemoModule } from './data/table/tabledemo.module';
import { SoptorshiVirtualScrollerDemoModule } from './data/virtualscroller/virtualscrollerdemo.module';
import { SoptorshiPickListDemoModule } from './data/picklist/picklistdemo.module';
import { SoptorshiOrderListDemoModule } from './data/orderlist/orderlistdemo.module';
import { SoptorshiFullCalendarDemoModule } from './data/fullcalendar/fullcalendardemo.module';
import { SoptorshiTreeDemoModule } from './data/tree/treedemo.module';
import { SoptorshiTreeTableDemoModule } from './data/treetable/treetabledemo.module';
import { SoptorshiPaginatorDemoModule } from './data/paginator/paginatordemo.module';
import { SoptorshiGmapDemoModule } from './data/gmap/gmapdemo.module';
import { SoptorshiOrgChartDemoModule } from './data/orgchart/orgchartdemo.module';
import { SoptorshiCarouselDemoModule } from './data/carousel/carouseldemo.module';
import { SoptorshiDataViewDemoModule } from './data/dataview/dataviewdemo.module';

import { SoptorshiBarchartDemoModule } from './charts/barchart/barchartdemo.module';
import { SoptorshiDoughnutchartDemoModule } from './charts/doughnutchart/doughnutchartdemo.module';
import { SoptorshiLinechartDemoModule } from './charts/linechart/linechartdemo.module';
import { SoptorshiPiechartDemoModule } from './charts/piechart/piechartdemo.module';
import { SoptorshiPolarareachartDemoModule } from './charts/polarareachart/polarareachartdemo.module';
import { SoptorshiRadarchartDemoModule } from './charts/radarchart/radarchartdemo.module';

import { SoptorshiDragDropDemoModule } from './dragdrop/dragdrop/dragdropdemo.module';

import { SoptorshiMenuDemoModule } from './menu/menu/menudemo.module';
import { SoptorshiContextMenuDemoModule } from './menu/contextmenu/contextmenudemo.module';
import { SoptorshiPanelMenuDemoModule } from './menu/panelmenu/panelmenudemo.module';
import { SoptorshiStepsDemoModule } from './menu/steps/stepsdemo.module';
import { SoptorshiTieredMenuDemoModule } from './menu/tieredmenu/tieredmenudemo.module';
import { SoptorshiBreadcrumbDemoModule } from './menu/breadcrumb/breadcrumbdemo.module';
import { SoptorshiMegaMenuDemoModule } from './menu/megamenu/megamenudemo.module';
import { SoptorshiMenuBarDemoModule } from './menu/menubar/menubardemo.module';
import { SoptorshiSlideMenuDemoModule } from './menu/slidemenu/slidemenudemo.module';
import { SoptorshiTabMenuDemoModule } from './menu/tabmenu/tabmenudemo.module';

import { SoptorshiBlockUIDemoModule } from './misc/blockui/blockuidemo.module';
import { SoptorshiCaptchaDemoModule } from './misc/captcha/captchademo.module';
import { SoptorshiDeferDemoModule } from './misc/defer/deferdemo.module';
import { SoptorshiInplaceDemoModule } from './misc/inplace/inplacedemo.module';
import { SoptorshiProgressBarDemoModule } from './misc/progressbar/progressbardemo.module';
import { SoptorshiRTLDemoModule } from './misc/rtl/rtldemo.module';
import { SoptorshiTerminalDemoModule } from './misc/terminal/terminaldemo.module';
import { SoptorshiValidationDemoModule } from './misc/validation/validationdemo.module';
import { SoptorshiProgressSpinnerDemoModule } from './misc/progressspinner/progressspinnerdemo.module';

@NgModule({
    imports: [
        SoptorshiMenuDemoModule,
        SoptorshiContextMenuDemoModule,
        SoptorshiPanelMenuDemoModule,
        SoptorshiStepsDemoModule,
        SoptorshiTieredMenuDemoModule,
        SoptorshiBreadcrumbDemoModule,
        SoptorshiMegaMenuDemoModule,
        SoptorshiMenuBarDemoModule,
        SoptorshiSlideMenuDemoModule,
        SoptorshiTabMenuDemoModule,

        SoptorshiBlockUIDemoModule,
        SoptorshiCaptchaDemoModule,
        SoptorshiDeferDemoModule,
        SoptorshiInplaceDemoModule,
        SoptorshiProgressBarDemoModule,
        SoptorshiInputMaskDemoModule,
        SoptorshiRTLDemoModule,
        SoptorshiTerminalDemoModule,
        SoptorshiValidationDemoModule,

        SoptorshiButtonDemoModule,
        SoptorshiSplitbuttonDemoModule,

        SoptorshiInputTextDemoModule,
        SoptorshiInputTextAreaDemoModule,
        SoptorshiInputGroupDemoModule,
        SoptorshiCalendarDemoModule,
        SoptorshiChipsDemoModule,
        SoptorshiInputMaskDemoModule,
        SoptorshiInputSwitchDemoModule,
        SoptorshiPasswordIndicatorDemoModule,
        SoptorshiAutoCompleteDemoModule,
        SoptorshiSliderDemoModule,
        SoptorshiSpinnerDemoModule,
        SoptorshiRatingDemoModule,
        SoptorshiSelectDemoModule,
        SoptorshiSelectButtonDemoModule,
        SoptorshiListboxDemoModule,
        SoptorshiRadioButtonDemoModule,
        SoptorshiToggleButtonDemoModule,
        SoptorshiEditorDemoModule,
        SoptorshiColorPickerDemoModule,
        SoptorshiCheckboxDemoModule,
        SoptorshiKeyFilterDemoModule,

        SoptorshiMessagesDemoModule,
        SoptorshiToastDemoModule,
        SoptorshiGalleriaDemoModule,

        SoptorshiFileUploadDemoModule,

        SoptorshiAccordionDemoModule,
        SoptorshiPanelDemoModule,
        SoptorshiTabViewDemoModule,
        SoptorshiFieldsetDemoModule,
        SoptorshiToolbarDemoModule,
        SoptorshiScrollPanelDemoModule,
        SoptorshiCardDemoModule,
        SoptorshiFlexGridDemoModule,

        SoptorshiBarchartDemoModule,
        SoptorshiDoughnutchartDemoModule,
        SoptorshiLinechartDemoModule,
        SoptorshiPiechartDemoModule,
        SoptorshiPolarareachartDemoModule,
        SoptorshiRadarchartDemoModule,

        SoptorshiDragDropDemoModule,

        SoptorshiDialogDemoModule,
        SoptorshiConfirmDialogDemoModule,
        SoptorshiLightboxDemoModule,
        SoptorshiTooltipDemoModule,
        SoptorshiOverlayPanelDemoModule,
        SoptorshiSideBarDemoModule,

        SoptorshiTableDemoModule,
        SoptorshiDataViewDemoModule,
        SoptorshiVirtualScrollerDemoModule,
        SoptorshiFullCalendarDemoModule,
        SoptorshiOrderListDemoModule,
        SoptorshiPickListDemoModule,
        SoptorshiTreeDemoModule,
        SoptorshiTreeTableDemoModule,
        SoptorshiPaginatorDemoModule,
        SoptorshiOrgChartDemoModule,
        SoptorshiGmapDemoModule,
        SoptorshiCarouselDemoModule,
        SoptorshiProgressSpinnerDemoModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiprimengModule {}
