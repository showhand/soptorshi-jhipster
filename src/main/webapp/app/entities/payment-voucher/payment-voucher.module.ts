import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    PaymentVoucherComponent,
    PaymentVoucherDetailComponent,
    PaymentVoucherUpdateComponent,
    PaymentVoucherDeletePopupComponent,
    PaymentVoucherDeleteDialogComponent,
    paymentVoucherRoute,
    paymentVoucherPopupRoute
} from './';

const ENTITY_STATES = [...paymentVoucherRoute, ...paymentVoucherPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /*declarations: [
        PaymentVoucherComponent,
        PaymentVoucherDetailComponent,
        PaymentVoucherUpdateComponent,
        PaymentVoucherDeleteDialogComponent,
        PaymentVoucherDeletePopupComponent
    ],
    entryComponents: [
        PaymentVoucherComponent,
        PaymentVoucherUpdateComponent,
        PaymentVoucherDeleteDialogComponent,
        PaymentVoucherDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiPaymentVoucherModule {}
