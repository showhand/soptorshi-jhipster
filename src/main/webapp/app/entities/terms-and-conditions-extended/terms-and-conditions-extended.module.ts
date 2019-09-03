import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import { TermsAndConditionsExtendedComponent } from 'app/entities/terms-and-conditions-extended/terms-and-conditions-extended.component';
import {
    TermsAndConditionsComponent,
    TermsAndConditionsDeleteDialogComponent,
    TermsAndConditionsDeletePopupComponent,
    TermsAndConditionsDetailComponent,
    TermsAndConditionsUpdateComponent
} from 'app/entities/terms-and-conditions';
import { TermsAndConditionsExtendedUpdateComponent } from 'app/entities/terms-and-conditions-extended/terms-and-conditions-extended-update.component';
import { TermsAndConditionsExtendedDetailComponent } from 'app/entities/terms-and-conditions-extended/terms-and-conditions-extended-detail.component';
import {
    termsAndConditionsExtendedPopupRoute,
    termsAndConditionsExtendedRoute
} from 'app/entities/terms-and-conditions-extended/terms-and-conditions-extended.route';

const ENTITY_STATES = [...termsAndConditionsExtendedRoute, ...termsAndConditionsExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TermsAndConditionsComponent,
        TermsAndConditionsDetailComponent,
        TermsAndConditionsUpdateComponent,
        TermsAndConditionsExtendedComponent,
        TermsAndConditionsExtendedDetailComponent,
        TermsAndConditionsExtendedUpdateComponent,
        TermsAndConditionsDeleteDialogComponent,
        TermsAndConditionsDeletePopupComponent
    ],
    entryComponents: [
        TermsAndConditionsExtendedComponent,
        TermsAndConditionsExtendedUpdateComponent,
        TermsAndConditionsDeleteDialogComponent,
        TermsAndConditionsDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiTermsAndConditionsModule {}
