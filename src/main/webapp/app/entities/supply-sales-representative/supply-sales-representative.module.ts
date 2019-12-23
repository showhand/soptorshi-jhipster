import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplySalesRepresentativeComponent,
    SupplySalesRepresentativeDetailComponent,
    SupplySalesRepresentativeUpdateComponent,
    SupplySalesRepresentativeDeletePopupComponent,
    SupplySalesRepresentativeDeleteDialogComponent,
    supplySalesRepresentativeRoute,
    supplySalesRepresentativePopupRoute
} from './';

const ENTITY_STATES = [...supplySalesRepresentativeRoute, ...supplySalesRepresentativePopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplySalesRepresentativeComponent,
        SupplySalesRepresentativeDetailComponent,
        SupplySalesRepresentativeUpdateComponent,
        SupplySalesRepresentativeDeleteDialogComponent,
        SupplySalesRepresentativeDeletePopupComponent
    ],
    entryComponents: [
        SupplySalesRepresentativeComponent,
        SupplySalesRepresentativeUpdateComponent,
        SupplySalesRepresentativeDeleteDialogComponent,
        SupplySalesRepresentativeDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplySalesRepresentativeModule {}
