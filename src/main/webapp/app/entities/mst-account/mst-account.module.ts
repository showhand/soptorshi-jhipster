import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    MstAccountComponent,
    MstAccountDetailComponent,
    MstAccountUpdateComponent,
    MstAccountDeletePopupComponent,
    MstAccountDeleteDialogComponent,
    mstAccountRoute,
    mstAccountPopupRoute
} from './';

const ENTITY_STATES = [...mstAccountRoute, ...mstAccountPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    // declarations: [
    //     MstAccountComponent,
    //     MstAccountDetailComponent,
    //     MstAccountUpdateComponent,
    //     MstAccountDeleteDialogComponent,
    //     MstAccountDeletePopupComponent
    // ],
    // entryComponents: [MstAccountComponent, MstAccountUpdateComponent, MstAccountDeleteDialogComponent, MstAccountDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiMstAccountModule {}
