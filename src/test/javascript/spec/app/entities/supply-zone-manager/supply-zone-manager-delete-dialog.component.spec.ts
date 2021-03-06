/* tslint:disable max-line-length */
import { ComponentFixture, fakeAsync, inject, TestBed, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SoptorshiTestModule } from '../../../test.module';
import { SupplyZoneManagerDeleteDialogComponent } from 'app/entities/supply-zone-manager/supply-zone-manager-delete-dialog.component';
import { SupplyZoneManagerService } from 'app/entities/supply-zone-manager/supply-zone-manager.service';

describe('Component Tests', () => {
    describe('SupplyZoneManager Management Delete Component', () => {
        let comp: SupplyZoneManagerDeleteDialogComponent;
        let fixture: ComponentFixture<SupplyZoneManagerDeleteDialogComponent>;
        let service: SupplyZoneManagerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [SupplyZoneManagerDeleteDialogComponent]
            })
                .overrideTemplate(SupplyZoneManagerDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SupplyZoneManagerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SupplyZoneManagerService);
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
