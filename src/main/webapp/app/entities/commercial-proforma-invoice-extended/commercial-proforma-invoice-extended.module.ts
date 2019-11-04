import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    CommercialProformaInvoiceDeleteDialogExtendedComponent,
    CommercialProformaInvoiceDeletePopupExtendedComponent,
    CommercialProformaInvoiceDetailExtendedComponent,
    CommercialProformaInvoiceExtendedComponent,
    commercialProformaInvoiceExtendedRoute,
    commercialProformaInvoicePopupExtendedRoute,
    CommercialProformaInvoiceUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...commercialProformaInvoiceExtendedRoute, ...commercialProformaInvoicePopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        CommercialProformaInvoiceExtendedComponent,
        CommercialProformaInvoiceDetailExtendedComponent,
        CommercialProformaInvoiceUpdateExtendedComponent,
        CommercialProformaInvoiceDeleteDialogExtendedComponent,
        CommercialProformaInvoiceDeletePopupExtendedComponent
    ],
    entryComponents: [
        CommercialProformaInvoiceExtendedComponent,
        CommercialProformaInvoiceUpdateExtendedComponent,
        CommercialProformaInvoiceDeleteDialogExtendedComponent,
        CommercialProformaInvoiceDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiCommercialProformaInvoiceExtendedModule {}
