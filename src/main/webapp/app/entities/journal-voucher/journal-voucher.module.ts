import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    JournalVoucherComponent,
    JournalVoucherDetailComponent,
    JournalVoucherUpdateComponent,
    JournalVoucherDeletePopupComponent,
    JournalVoucherDeleteDialogComponent,
    journalVoucherRoute,
    journalVoucherPopupRoute
} from './';

const ENTITY_STATES = [...journalVoucherRoute, ...journalVoucherPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /* declarations: [
        JournalVoucherComponent,
        JournalVoucherDetailComponent,
        JournalVoucherUpdateComponent,
        JournalVoucherDeleteDialogComponent,
        JournalVoucherDeletePopupComponent
    ],
    entryComponents: [
        JournalVoucherComponent,
        JournalVoucherUpdateComponent,
        JournalVoucherDeleteDialogComponent,
        JournalVoucherDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiJournalVoucherModule {}
