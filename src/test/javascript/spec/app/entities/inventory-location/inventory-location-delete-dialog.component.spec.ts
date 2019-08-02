/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { InventoryLocationDeleteDialogComponent } from 'app/entities/inventory-location/inventory-location-delete-dialog.component';
import { InventoryLocationService } from 'app/entities/inventory-location/inventory-location.service';

describe('Component Tests', () => {
    describe('InventoryLocation Management Delete Component', () => {
        let comp: InventoryLocationDeleteDialogComponent;
        let fixture: ComponentFixture<InventoryLocationDeleteDialogComponent>;
        let service: InventoryLocationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [InventoryLocationDeleteDialogComponent]
            })
                .overrideTemplate(InventoryLocationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InventoryLocationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InventoryLocationService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
