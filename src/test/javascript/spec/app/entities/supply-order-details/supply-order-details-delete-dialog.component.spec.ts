/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyOrderDetailsDeleteDialogComponent } from 'app/entities/supply-order-details/supply-order-details-delete-dialog.component';
import { SupplyOrderDetailsService } from 'app/entities/supply-order-details/supply-order-details.service';

describe('Component Tests', () => {
    describe('SupplyOrderDetails Management Delete Component', () => {
        let comp: SupplyOrderDetailsDeleteDialogComponent;
        let fixture: ComponentFixture<SupplyOrderDetailsDeleteDialogComponent>;
        let service: SupplyOrderDetailsService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyOrderDetailsDeleteDialogComponent]
            })
                .overrideTemplate(SupplyOrderDetailsDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplyOrderDetailsDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplyOrderDetailsService);
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
