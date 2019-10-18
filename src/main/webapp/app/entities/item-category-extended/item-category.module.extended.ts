import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ItemCategoryComponentExtended,
    ItemCategoryDetailComponentExtended,
    ItemCategoryUpdateComponentExtended,
    ItemCategoryDeletePopupComponentExtended,
    ItemCategoryDeleteDialogComponentExtended,
    itemCategoryRouteExtended,
    itemCategoryPopupRouteExtended
} from './';

const ENTITY_STATES = [...itemCategoryRouteExtended, ...itemCategoryPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ItemCategoryComponentExtended,
        ItemCategoryDetailComponentExtended,
        ItemCategoryUpdateComponentExtended,
        ItemCategoryDeleteDialogComponentExtended,
        ItemCategoryDeletePopupComponentExtended
    ],
    entryComponents: [
        ItemCategoryComponentExtended,
        ItemCategoryUpdateComponentExtended,
        ItemCategoryDeleteDialogComponentExtended,
        ItemCategoryDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiItemCategoryModuleExtended {}
