import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    VoucherNumberControlComponent,
    VoucherNumberControlDetailComponent,
    VoucherNumberControlUpdateComponent,
    VoucherNumberControlDeletePopupComponent,
    VoucherNumberControlDeleteDialogComponent,
    voucherNumberControlRoute,
    voucherNumberControlPopupRoute
} from './';

const ENTITY_STATES = [...voucherNumberControlRoute, ...voucherNumberControlPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /*    declarations: [
        VoucherNumberControlComponent,
        VoucherNumberControlDetailComponent,
        VoucherNumberControlUpdateComponent,
        VoucherNumberControlDeleteDialogComponent,
        VoucherNumberControlDeletePopupComponent
    ],
    entryComponents: [
        VoucherNumberControlComponent,
        VoucherNumberControlUpdateComponent,
        VoucherNumberControlDeleteDialogComponent,
        VoucherNumberControlDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiVoucherNumberControlModule {}
