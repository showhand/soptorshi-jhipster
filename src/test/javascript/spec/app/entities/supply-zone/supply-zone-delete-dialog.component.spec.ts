/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyZoneDeleteDialogComponent } from 'app/entities/supply-zone/supply-zone-delete-dialog.component';
import { SupplyZoneService } from 'app/entities/supply-zone/supply-zone.service';

describe('Component Tests', () => {
    describe('SupplyZone Management Delete Component', () => {
        let comp: SupplyZoneDeleteDialogComponent;
        let fixture: ComponentFixture<SupplyZoneDeleteDialogComponent>;
        let service: SupplyZoneService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyZoneDeleteDialogComponent]
            })
                .overrideTemplate(SupplyZoneDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplyZoneDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplyZoneService);
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
