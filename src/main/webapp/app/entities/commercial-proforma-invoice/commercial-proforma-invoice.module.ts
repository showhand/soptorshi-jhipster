import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialProformaInvoiceComponent,
    CommercialProformaInvoiceDeleteDialogComponent,
    CommercialProformaInvoiceDeletePopupComponent,
    CommercialProformaInvoiceDetailComponent,
    commercialProformaInvoicePopupRoute,
    commercialProformaInvoiceRoute,
    CommercialProformaInvoiceUpdateComponent
} from './';

const ENTITY_STATES = [...commercialProformaInvoiceRoute, ...commercialProformaInvoicePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialProformaInvoiceComponent,
        CommercialProformaInvoiceDetailComponent,
        CommercialProformaInvoiceUpdateComponent,
        CommercialProformaInvoiceDeleteDialogComponent,
        CommercialProformaInvoiceDeletePopupComponent
    ],
    entryComponents: [
        CommercialProformaInvoiceComponent,
        CommercialProformaInvoiceUpdateComponent,
        CommercialProformaInvoiceDeleteDialogComponent,
        CommercialProformaInvoiceDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialProformaInvoiceModule {}
