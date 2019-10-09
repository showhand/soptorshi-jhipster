import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    MstAccountExtendedComponent,
    MstAccountExtendedDetailComponent,
    mstAccountExtendedPopupRoute,
    mstAccountExtendedRoute,
    MstAccountExtendedUpdateComponent
} from './';
import {
    MstAccountComponent,
    MstAccountDeleteDialogComponent,
    MstAccountDeletePopupComponent,
    MstAccountDetailComponent,
    MstAccountUpdateComponent
} from 'app/entities/mst-account';

const ENTITY_STATES = [...mstAccountExtendedRoute, ...mstAccountExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MstAccountComponent,
        MstAccountDetailComponent,
        MstAccountUpdateComponent,
        MstAccountExtendedComponent,
        MstAccountExtendedDetailComponent,
        MstAccountExtendedUpdateComponent,
        MstAccountDeleteDialogComponent,
        MstAccountDeletePopupComponent
    ],
    entryComponents: [
        MstAccountExtendedComponent,
        MstAccountExtendedUpdateComponent,
        MstAccountDeleteDialogComponent,
        MstAccountDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiMstAccountModule {}
