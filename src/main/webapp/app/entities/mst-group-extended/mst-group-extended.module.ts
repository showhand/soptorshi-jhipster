import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    MstGroupExtendedComponent,
    MstGroupExtendedDetailComponent,
    MstGroupExtendedUpdateComponent,
    mstGroupExtendedRoute,
    mstGroupExtendedPopupRoute
} from './';
import {
    MstGroupComponent,
    MstGroupDeleteDialogComponent,
    MstGroupDeletePopupComponent,
    MstGroupDetailComponent,
    MstGroupUpdateComponent
} from 'app/entities/mst-group';

const ENTITY_STATES = [...mstGroupExtendedRoute, ...mstGroupExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MstGroupComponent,
        MstGroupDetailComponent,
        MstGroupUpdateComponent,
        MstGroupExtendedComponent,
        MstGroupExtendedDetailComponent,
        MstGroupExtendedUpdateComponent,
        MstGroupDeleteDialogComponent,
        MstGroupDeletePopupComponent
    ],
    entryComponents: [
        MstGroupExtendedComponent,
        MstGroupExtendedUpdateComponent,
        MstGroupDeleteDialogComponent,
        MstGroupDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiMstGroupModule {}
