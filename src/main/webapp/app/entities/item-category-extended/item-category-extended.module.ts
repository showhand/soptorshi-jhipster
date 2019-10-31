import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ItemCategoryDeleteDialogExtendedComponent,
    ItemCategoryDeletePopupExtendedComponent,
    ItemCategoryDetailExtendedComponent,
    ItemCategoryExtendedComponent,
    itemCategoryExtendedRoute,
    itemCategoryPopupRouteExtended,
    ItemCategoryUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...itemCategoryExtendedRoute, ...itemCategoryPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ItemCategoryExtendedComponent,
        ItemCategoryDetailExtendedComponent,
        ItemCategoryUpdateExtendedComponent,
        ItemCategoryDeleteDialogExtendedComponent,
        ItemCategoryDeletePopupExtendedComponent
    ],
    entryComponents: [
        ItemCategoryExtendedComponent,
        ItemCategoryUpdateExtendedComponent,
        ItemCategoryDeleteDialogExtendedComponent,
        ItemCategoryDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiItemCategoryModuleExtended {}
