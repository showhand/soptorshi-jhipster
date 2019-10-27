import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    DtTransactionComponent,
    DtTransactionDetailComponent,
    DtTransactionUpdateComponent,
    DtTransactionDeletePopupComponent,
    DtTransactionDeleteDialogComponent,
    dtTransactionRoute,
    dtTransactionPopupRoute
} from './';

const ENTITY_STATES = [...dtTransactionRoute, ...dtTransactionPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /*declarations: [
        DtTransactionComponent,
        DtTransactionDetailComponent,
        DtTransactionUpdateComponent,
        DtTransactionDeleteDialogComponent,
        DtTransactionDeletePopupComponent
    ],
    entryComponents: [
        DtTransactionComponent,
        DtTransactionUpdateComponent,
        DtTransactionDeleteDialogComponent,
        DtTransactionDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiDtTransactionModule {}
