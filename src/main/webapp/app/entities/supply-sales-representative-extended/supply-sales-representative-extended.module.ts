import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplySalesRepresentativeDeleteDialogExtendedComponent,
    SupplySalesRepresentativeDeletePopupExtendedComponent,
    SupplySalesRepresentativeDetailExtendedComponent,
    SupplySalesRepresentativeExtendedComponent,
    supplySalesRepresentativeExtendedRoute,
    supplySalesRepresentativePopupExtendedRoute,
    SupplySalesRepresentativeUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...supplySalesRepresentativeExtendedRoute, ...supplySalesRepresentativePopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplySalesRepresentativeExtendedComponent,
        SupplySalesRepresentativeDetailExtendedComponent,
        SupplySalesRepresentativeUpdateExtendedComponent,
        SupplySalesRepresentativeDeleteDialogExtendedComponent,
        SupplySalesRepresentativeDeletePopupExtendedComponent
    ],
    entryComponents: [
        SupplySalesRepresentativeExtendedComponent,
        SupplySalesRepresentativeUpdateExtendedComponent,
        SupplySalesRepresentativeDeleteDialogExtendedComponent,
        SupplySalesRepresentativeDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplySalesRepresentativeExtendedModule {}
