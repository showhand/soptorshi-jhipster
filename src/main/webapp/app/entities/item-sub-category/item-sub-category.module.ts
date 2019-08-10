import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ItemSubCategoryComponent,
    ItemSubCategoryDetailComponent,
    ItemSubCategoryUpdateComponent,
    ItemSubCategoryDeletePopupComponent,
    ItemSubCategoryDeleteDialogComponent,
    itemSubCategoryRoute,
    itemSubCategoryPopupRoute
} from './';

const ENTITY_STATES = [...itemSubCategoryRoute, ...itemSubCategoryPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ItemSubCategoryComponent,
        ItemSubCategoryDetailComponent,
        ItemSubCategoryUpdateComponent,
        ItemSubCategoryDeleteDialogComponent,
        ItemSubCategoryDeletePopupComponent
    ],
    entryComponents: [
        ItemSubCategoryComponent,
        ItemSubCategoryUpdateComponent,
        ItemSubCategoryDeleteDialogComponent,
        ItemSubCategoryDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiItemSubCategoryModule {}
