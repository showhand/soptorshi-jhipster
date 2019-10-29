import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    JournalVoucherExtendedComponent,
    JournalVoucherExtendedDetailComponent,
    JournalVoucherExtendedUpdateComponent,
    journalVoucherExtendedRoute,
    journalVoucherExtendedPopupRoute
} from './';
import {
    JournalVoucherComponent,
    JournalVoucherDeleteDialogComponent,
    JournalVoucherDeletePopupComponent,
    JournalVoucherDetailComponent,
    JournalVoucherUpdateComponent
} from 'app/entities/journal-voucher';
import { JournalVoucherTransactionsComponent } from 'app/entities/journal-voucher-extended/journal-voucher-transactions.component';
import { JournalVoucherTransactionDetailComponent } from 'app/entities/journal-voucher-extended/journal-voucher-transaction-detail.component';
import { JournalVoucherTransactionUpdateComponent } from 'app/entities/journal-voucher-extended/journal-voucher-transaction-update.component';

const ENTITY_STATES = [...journalVoucherExtendedRoute, ...journalVoucherExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        JournalVoucherComponent,
        JournalVoucherDetailComponent,
        JournalVoucherUpdateComponent,
        JournalVoucherExtendedComponent,
        JournalVoucherExtendedDetailComponent,
        JournalVoucherExtendedUpdateComponent,
        JournalVoucherDeleteDialogComponent,
        JournalVoucherDeletePopupComponent,
        JournalVoucherTransactionsComponent,
        JournalVoucherTransactionDetailComponent,
        JournalVoucherTransactionUpdateComponent,
        JournalVoucherTransactionDetailComponent
    ],
    entryComponents: [
        JournalVoucherTransactionsComponent,
        JournalVoucherExtendedComponent,
        JournalVoucherExtendedUpdateComponent,
        JournalVoucherDeleteDialogComponent,
        JournalVoucherDeletePopupComponent,
        JournalVoucherTransactionDetailComponent,
        JournalVoucherTransactionUpdateComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiJournalVoucherModule {}
