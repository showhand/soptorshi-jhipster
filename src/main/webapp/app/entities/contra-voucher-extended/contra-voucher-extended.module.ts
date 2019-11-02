import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    ContraVoucherComponent,
    ContraVoucherDeleteDialogComponent,
    ContraVoucherDeletePopupComponent,
    ContraVoucherDetailComponent,
    ContraVoucherUpdateComponent
} from 'app/entities/contra-voucher';
import {
    contraVoucherExtendedPopupRoute,
    contraVoucherExtendedRoute
} from 'app/entities/contra-voucher-extended/contra-voucher-extended.route';
import { ContraVoucherExtendedComponent } from 'app/entities/contra-voucher-extended/contra-voucher-extended.component';
import { ContraVoucherExtendedUpdateComponent } from 'app/entities/contra-voucher-extended/contra-voucher-extended-update.component';

const ENTITY_STATES = [...contraVoucherExtendedRoute, ...contraVoucherExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ContraVoucherComponent,
        ContraVoucherDetailComponent,
        ContraVoucherUpdateComponent,
        ContraVoucherDeleteDialogComponent,
        ContraVoucherDeletePopupComponent,
        ContraVoucherExtendedComponent,
        ContraVoucherExtendedUpdateComponent
    ],
    entryComponents: [
        ContraVoucherComponent,
        ContraVoucherUpdateComponent,
        ContraVoucherDeleteDialogComponent,
        ContraVoucherDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiContraVoucherModule {}
