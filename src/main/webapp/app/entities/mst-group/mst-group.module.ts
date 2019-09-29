import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    MstGroupComponent,
    MstGroupDetailComponent,
    MstGroupUpdateComponent,
    MstGroupDeletePopupComponent,
    MstGroupDeleteDialogComponent,
    mstGroupRoute,
    mstGroupPopupRoute
} from './';

const ENTITY_STATES = [...mstGroupRoute, ...mstGroupPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /*    declarations: [
        MstGroupComponent,
        MstGroupDetailComponent,
        MstGroupUpdateComponent,
        MstGroupDeleteDialogComponent,
        MstGroupDeletePopupComponent
    ],
    entryComponents: [MstGroupComponent, MstGroupUpdateComponent, MstGroupDeleteDialogComponent, MstGroupDeletePopupComponent],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiMstGroupModule {}
