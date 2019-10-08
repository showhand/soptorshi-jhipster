import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    VoucherNumberControlExtendedComponent,
    VoucherNumberControlExtendedDetailComponent,
    VoucherNumberControlExtendedUpdateComponent,
    voucherNumberControlExtendedRoute,
    voucherNumberControlExtendedPopupRoute
} from './';
import {
    VoucherNumberControlComponent,
    VoucherNumberControlDeleteDialogComponent,
    VoucherNumberControlDeletePopupComponent,
    VoucherNumberControlDetailComponent,
    VoucherNumberControlUpdateComponent
} from 'app/entities/voucher-number-control';

const ENTITY_STATES = [...voucherNumberControlExtendedRoute, ...voucherNumberControlExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VoucherNumberControlComponent,
        VoucherNumberControlDetailComponent,
        VoucherNumberControlUpdateComponent,
        VoucherNumberControlExtendedComponent,
        VoucherNumberControlExtendedDetailComponent,
        VoucherNumberControlExtendedUpdateComponent,
        VoucherNumberControlDeleteDialogComponent,
        VoucherNumberControlDeletePopupComponent
    ],
    entryComponents: [
        VoucherNumberControlExtendedComponent,
        VoucherNumberControlExtendedUpdateComponent,
        VoucherNumberControlDeleteDialogComponent,
        VoucherNumberControlDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiVoucherNumberControlModule {}
