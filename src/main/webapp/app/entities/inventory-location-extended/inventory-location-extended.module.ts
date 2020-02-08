import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    InventoryLocationDeleteDialogExtendedComponent,
    InventoryLocationDeletePopupExtendedComponent,
    InventoryLocationDetailExtendedComponent,
    InventoryLocationExtendedComponent,
    inventoryLocationExtendedRoute,
    inventoryLocationPopupExtendedRoute,
    InventoryLocationUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...inventoryLocationExtendedRoute, ...inventoryLocationPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        InventoryLocationExtendedComponent,
        InventoryLocationDetailExtendedComponent,
        InventoryLocationUpdateExtendedComponent,
        InventoryLocationDeleteDialogExtendedComponent,
        InventoryLocationDeletePopupExtendedComponent
    ],
    entryComponents: [
        InventoryLocationExtendedComponent,
        InventoryLocationUpdateExtendedComponent,
        InventoryLocationDeleteDialogExtendedComponent,
        InventoryLocationDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiInventoryLocationExtendedModule {}
