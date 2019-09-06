import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    DebtorLedgerComponent,
    DebtorLedgerDetailComponent,
    DebtorLedgerUpdateComponent,
    DebtorLedgerDeletePopupComponent,
    DebtorLedgerDeleteDialogComponent,
    debtorLedgerRoute,
    debtorLedgerPopupRoute
} from './';

const ENTITY_STATES = [...debtorLedgerRoute, ...debtorLedgerPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DebtorLedgerComponent,
        DebtorLedgerDetailComponent,
        DebtorLedgerUpdateComponent,
        DebtorLedgerDeleteDialogComponent,
        DebtorLedgerDeletePopupComponent
    ],
    entryComponents: [
        DebtorLedgerComponent,
        DebtorLedgerUpdateComponent,
        DebtorLedgerDeleteDialogComponent,
        DebtorLedgerDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiDebtorLedgerModule {}
