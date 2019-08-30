import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    QuotationExtendedComponent,
    QuotationExtendedDetailComponent,
    QuotationExtendedUpdateComponent,
    quotationExtendedRoute,
    quotationExtendedPopupRoute
} from './';
import {
    QuotationComponent,
    QuotationDeleteDialogComponent,
    QuotationDeletePopupComponent,
    QuotationDetailComponent,
    QuotationUpdateComponent
} from 'app/entities/quotation';
import { QuotationDetailsExtendedDirectiveForQuotation } from 'app/entities/quotation-extended/quotation-details-directive-for-quotation.component';

const ENTITY_STATES = [...quotationExtendedRoute, ...quotationExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        QuotationComponent,
        QuotationDetailComponent,
        QuotationUpdateComponent,
        QuotationExtendedComponent,
        QuotationExtendedDetailComponent,
        QuotationExtendedUpdateComponent,
        QuotationDeleteDialogComponent,
        QuotationDeletePopupComponent,
        QuotationDetailsExtendedDirectiveForQuotation
    ],
    entryComponents: [
        QuotationExtendedComponent,
        QuotationExtendedUpdateComponent,
        QuotationDeleteDialogComponent,
        QuotationDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiQuotationExtendedModule {}
