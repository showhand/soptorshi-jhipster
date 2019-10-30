import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ItemCategoryDeleteDialogExtendedComponent,
    ItemCategoryDeletePopupComponentExtended,
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
        ItemCategoryDeletePopupComponentExtended
    ],
    entryComponents: [
        ItemCategoryExtendedComponent,
        ItemCategoryUpdateExtendedComponent,
        ItemCategoryDeleteDialogExtendedComponent,
        ItemCategoryDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiItemCategoryModuleExtended {}
