import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ScmOrderDetailsFilterPipe, SoptorshiSharedModule } from 'app/shared';
import {
    SupplyAreaWiseAccumulationDeleteDialogExtendedComponent,
    SupplyAreaWiseAccumulationDeletePopupExtendedComponent,
    SupplyAreaWiseAccumulationDetailExtendedComponent,
    SupplyAreaWiseAccumulationExtendedComponent,
    supplyAreaWiseAccumulationExtendedRoute,
    supplyAreaWiseAccumulationPopupExtendedRoute,
    SupplyAreaWiseAccumulationUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...supplyAreaWiseAccumulationExtendedRoute, ...supplyAreaWiseAccumulationPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    providers: [ScmOrderDetailsFilterPipe],
    declarations: [
        SupplyAreaWiseAccumulationExtendedComponent,
        SupplyAreaWiseAccumulationDetailExtendedComponent,
        SupplyAreaWiseAccumulationUpdateExtendedComponent,
        SupplyAreaWiseAccumulationDeleteDialogExtendedComponent,
        SupplyAreaWiseAccumulationDeletePopupExtendedComponent
    ],
    entryComponents: [
        SupplyAreaWiseAccumulationExtendedComponent,
        SupplyAreaWiseAccumulationUpdateExtendedComponent,
        SupplyAreaWiseAccumulationDeleteDialogExtendedComponent,
        SupplyAreaWiseAccumulationDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyAreaWiseAccumulationExtendedModule {}
