/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { InventorySubLocationDeleteDialogComponent } from 'app/entities/inventory-sub-location/inventory-sub-location-delete-dialog.component';
import { InventorySubLocationService } from 'app/entities/inventory-sub-location/inventory-sub-location.service';

describe('Component Tests', () => {
    describe('InventorySubLocation Management Delete Component', () => {
        let comp: InventorySubLocationDeleteDialogComponent;
        let fixture: ComponentFixture<InventorySubLocationDeleteDialogComponent>;
        let service: InventorySubLocationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [InventorySubLocationDeleteDialogComponent]
            })
                .overrideTemplate(InventorySubLocationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(InventorySubLocationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InventorySubLocationService);
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
