import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    DtTransactionExtendedComponent,
    DtTransactionExtendedDetailComponent,
    DtTransactionExtendedUpdateComponent,
    dtTransactionExtendedRoute,
    dtTransactionExtendedPopupRoute
} from './';
import {
    DtTransactionComponent,
    DtTransactionDeleteDialogComponent,
    DtTransactionDeletePopupComponent,
    DtTransactionDetailComponent,
    dtTransactionPopupRoute,
    DtTransactionUpdateComponent
} from 'app/entities/dt-transaction';

const ENTITY_STATES = [...dtTransactionExtendedRoute, ...dtTransactionExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DtTransactionComponent,
        DtTransactionDetailComponent,
        DtTransactionUpdateComponent,
        DtTransactionExtendedComponent,
        DtTransactionExtendedDetailComponent,
        DtTransactionExtendedUpdateComponent,
        DtTransactionDeleteDialogComponent,
        DtTransactionDeletePopupComponent
    ],
    entryComponents: [
        DtTransactionExtendedComponent,
        DtTransactionExtendedUpdateComponent,
        DtTransactionDeleteDialogComponent,
        DtTransactionDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiDtTransactionModule {}
