import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ChequeRegisterComponent,
    ChequeRegisterDetailComponent,
    ChequeRegisterUpdateComponent,
    ChequeRegisterDeletePopupComponent,
    ChequeRegisterDeleteDialogComponent,
    chequeRegisterRoute,
    chequeRegisterPopupRoute
} from './';

const ENTITY_STATES = [...chequeRegisterRoute, ...chequeRegisterPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ChequeRegisterComponent,
        ChequeRegisterDetailComponent,
        ChequeRegisterUpdateComponent,
        ChequeRegisterDeleteDialogComponent,
        ChequeRegisterDeletePopupComponent
    ],
    entryComponents: [
        ChequeRegisterComponent,
        ChequeRegisterUpdateComponent,
        ChequeRegisterDeleteDialogComponent,
        ChequeRegisterDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiChequeRegisterModule {}
