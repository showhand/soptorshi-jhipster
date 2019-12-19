/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialWorkOrderDeleteDialogComponent } from 'app/entities/commercial-work-order/commercial-work-order-delete-dialog.component';
import { CommercialWorkOrderService } from 'app/entities/commercial-work-order/commercial-work-order.service';

describe('Component Tests', () => {
    describe('CommercialWorkOrder Management Delete Component', () => {
        let comp: CommercialWorkOrderDeleteDialogComponent;
        let fixture: ComponentFixture<CommercialWorkOrderDeleteDialogComponent>;
        let service: CommercialWorkOrderService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialWorkOrderDeleteDialogComponent]
            })
                .overrideTemplate(CommercialWorkOrderDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialWorkOrderDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommercialWorkOrderService);
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
