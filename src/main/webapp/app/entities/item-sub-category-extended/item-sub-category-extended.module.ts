import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ItemSubCategoryDeleteDialogExtendedComponent,
    ItemSubCategoryDeletePopupComponentExtended,
    ItemSubCategoryDetailExtendedComponent,
    ItemSubCategoryExtendedComponent,
    itemSubCategoryExtendedRoute,
    itemSubCategoryPopupRouteExtended,
    ItemSubCategoryUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...itemSubCategoryExtendedRoute, ...itemSubCategoryPopupRouteExtended];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ItemSubCategoryExtendedComponent,
        ItemSubCategoryDetailExtendedComponent,
        ItemSubCategoryUpdateExtendedComponent,
        ItemSubCategoryDeleteDialogExtendedComponent,
        ItemSubCategoryDeletePopupComponentExtended
    ],
    entryComponents: [
        ItemSubCategoryExtendedComponent,
        ItemSubCategoryUpdateExtendedComponent,
        ItemSubCategoryDeleteDialogExtendedComponent,
        ItemSubCategoryDeletePopupComponentExtended
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiItemSubCategoryModuleExtended {}
