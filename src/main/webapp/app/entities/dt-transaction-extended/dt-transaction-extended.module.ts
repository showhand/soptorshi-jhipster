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
import { GeneralLedgerReportComponent } from 'app/entities/dt-transaction-extended/general-ledger-report.component';

const ENTITY_STATES = [...dtTransactionExtendedRoute, ...dtTransactionExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule],
    declarations: [
        DtTransactionComponent,
        DtTransactionDetailComponent,
        DtTransactionUpdateComponent,
        DtTransactionExtendedComponent,
        DtTransactionExtendedDetailComponent,
        DtTransactionExtendedUpdateComponent,
        DtTransactionDeleteDialogComponent,
        DtTransactionDeletePopupComponent,
        GeneralLedgerReportComponent
    ],
    entryComponents: [
        DtTransactionExtendedComponent,
        DtTransactionExtendedUpdateComponent,
        DtTransactionDeleteDialogComponent,
        DtTransactionDeletePopupComponent,
        GeneralLedgerReportComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiDtTransactionModule {}
