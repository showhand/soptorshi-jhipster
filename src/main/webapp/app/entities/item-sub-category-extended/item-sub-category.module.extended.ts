import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ItemSubCategoryComponentExtended,
    ItemSubCategoryDetailComponentExtended,
    ItemSubCategoryUpdateComponentExtended,
    ItemSubCategoryDeletePopupComponentExtended,
    ItemSubCategoryDeleteDialogComponentExtended,
    itemSubCategoryRouteExtended,
    itemSubCategoryPopupRouteExtended
} from './';

const ENTITY_STATES = [...itemSubCategoryRouteExtended, ...itemSubCategoryPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ItemSubCategoryComponentExtended,
        ItemSubCategoryDetailComponentExtended,
        ItemSubCategoryUpdateComponentExtended,
        ItemSubCategoryDeleteDialogComponentExtended,
        ItemSubCategoryDeletePopupComponentExtended
    ],
    entryComponents: [
        ItemSubCategoryComponentExtended,
        ItemSubCategoryUpdateComponentExtended,
        ItemSubCategoryDeleteDialogComponentExtended,
        ItemSubCategoryDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiItemSubCategoryModuleExtended {}
