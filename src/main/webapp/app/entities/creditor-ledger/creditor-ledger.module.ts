import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CreditorLedgerComponent,
    CreditorLedgerDetailComponent,
    CreditorLedgerUpdateComponent,
    CreditorLedgerDeletePopupComponent,
    CreditorLedgerDeleteDialogComponent,
    creditorLedgerRoute,
    creditorLedgerPopupRoute
} from './';

const ENTITY_STATES = [...creditorLedgerRoute, ...creditorLedgerPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CreditorLedgerComponent,
        CreditorLedgerDetailComponent,
        CreditorLedgerUpdateComponent,
        CreditorLedgerDeleteDialogComponent,
        CreditorLedgerDeletePopupComponent
    ],
    entryComponents: [
        CreditorLedgerComponent,
        CreditorLedgerUpdateComponent,
        CreditorLedgerDeleteDialogComponent,
        CreditorLedgerDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCreditorLedgerModule {}
