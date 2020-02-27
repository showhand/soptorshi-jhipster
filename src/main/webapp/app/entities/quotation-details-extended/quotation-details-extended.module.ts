import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import { quotationDetailsExtendedRoute } from 'app/entities/quotation-details-extended/quotation-details-extended.route';
import { QuotationDetailsExtendedComponent } from 'app/entities/quotation-details-extended/quotation-details-extended.component';
import { QuotationDetailsExtendedDetailComponent } from 'app/entities/quotation-details-extended/quotation-details-extended-detail.component';
import { QuotationDetailsExtendedUpdateComponent } from 'app/entities/quotation-details-extended/quotation-details-extended-update.component';

const ENTITY_STATES = [...quotationDetailsExtendedRoute, ...quotationDetailsExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        //QuotationDetailsComponent,
        //QuotationDetailsDetailComponent,
        //QuotationDetailsUpdateComponent,
        QuotationDetailsExtendedComponent,
        QuotationDetailsExtendedDetailComponent,
        QuotationDetailsExtendedUpdateComponent
        //QuotationDetailsDeleteDialogComponent,
        //QuotationDetailsDeletePopupComponent
    ],
    entryComponents: [
        QuotationDetailsExtendedComponent,
        QuotationDetailsExtendedUpdateComponent
        //QuotationDetailsDeleteDialogComponent,
        //QuotationDetailsDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiQuotationDetailsExtendedModule {}
