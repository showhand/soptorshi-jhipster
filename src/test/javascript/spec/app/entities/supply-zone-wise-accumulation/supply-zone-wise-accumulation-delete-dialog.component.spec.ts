/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyZoneWiseAccumulationDeleteDialogComponent } from 'app/entities/supply-zone-wise-accumulation/supply-zone-wise-accumulation-delete-dialog.component';
import { SupplyZoneWiseAccumulationService } from 'app/entities/supply-zone-wise-accumulation/supply-zone-wise-accumulation.service';

describe('Component Tests', () => {
    describe('SupplyZoneWiseAccumulation Management Delete Component', () => {
        let comp: SupplyZoneWiseAccumulationDeleteDialogComponent;
        let fixture: ComponentFixture<SupplyZoneWiseAccumulationDeleteDialogComponent>;
        let service: SupplyZoneWiseAccumulationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyZoneWiseAccumulationDeleteDialogComponent]
            })
                .overrideTemplate(SupplyZoneWiseAccumulationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplyZoneWiseAccumulationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplyZoneWiseAccumulationService);
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
