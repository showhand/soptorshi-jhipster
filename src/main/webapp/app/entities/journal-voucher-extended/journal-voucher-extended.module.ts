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
        JournalVoucherDeletePopupComponent
    ],
    entryComponents: [
        JournalVoucherExtendedComponent,
        JournalVoucherExtendedUpdateComponent,
        JournalVoucherDeleteDialogComponent,
        JournalVoucherDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiJournalVoucherModule {}
