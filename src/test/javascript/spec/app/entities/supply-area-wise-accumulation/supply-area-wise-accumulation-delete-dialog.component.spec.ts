/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyAreaWiseAccumulationDeleteDialogComponent } from 'app/entities/supply-area-wise-accumulation/supply-area-wise-accumulation-delete-dialog.component';
import { SupplyAreaWiseAccumulationService } from 'app/entities/supply-area-wise-accumulation/supply-area-wise-accumulation.service';

describe('Component Tests', () => {
    describe('SupplyAreaWiseAccumulation Management Delete Component', () => {
        let comp: SupplyAreaWiseAccumulationDeleteDialogComponent;
        let fixture: ComponentFixture<SupplyAreaWiseAccumulationDeleteDialogComponent>;
        let service: SupplyAreaWiseAccumulationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyAreaWiseAccumulationDeleteDialogComponent]
            })
                .overrideTemplate(SupplyAreaWiseAccumulationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplyAreaWiseAccumulationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplyAreaWiseAccumulationService);
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
